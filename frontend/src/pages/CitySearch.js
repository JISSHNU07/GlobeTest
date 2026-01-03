import React, { useEffect, useState } from 'react';
import api from '../services/api';
import './CitySearch.css';

const CitySearch = () => {
  const [cities, setCities] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchPopularCities();
  }, []);

  const fetchPopularCities = async () => {
    setLoading(true);
    try {
      const response = await api.get('/api/cities/popular');
      setCities(response.data);
    } catch (error) {
      console.error('Error fetching cities:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await api.get(`/api/cities?query=${searchQuery}`);
      setCities(response.data);
    } catch (error) {
      console.error('Error searching cities:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h1>Search Cities</h1>
      <div className="card">
        <form onSubmit={handleSearch} className="search-form">
          <input
            type="text"
            placeholder="Search by city or country..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="search-input"
          />
          <button type="submit" className="btn btn-primary">
            Search
          </button>
        </form>
      </div>

      {loading ? (
        <div className="loading">Loading...</div>
      ) : (
        <div className="cities-grid">
          {cities.map(city => (
            <div key={city.id} className="city-card">
              <h3>{city.name}</h3>
              <p className="city-country">{city.country}</p>
              {city.description && <p className="city-description">{city.description}</p>}
              <div className="city-stats">
                <span>Cost Index: {city.costIndex}</span>
                <span>Popularity: {city.popularityScore}</span>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default CitySearch;




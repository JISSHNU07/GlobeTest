import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import api from '../services/api';
import './ItineraryBuilder.css';

const ItineraryBuilder = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [trip, setTrip] = useState(null);
  const [stops, setStops] = useState([]);
  const [cities, setCities] = useState([]);
  const [showCitySearch, setShowCitySearch] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchData();
  }, [id]);

  const fetchData = async () => {
    try {
      const [tripRes, stopsRes] = await Promise.all([
        api.get(`/api/trips/${id}`),
        api.get(`/api/trips/${id}/stops`)
      ]);
      setTrip(tripRes.data);
      setStops(stopsRes.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false);
    }
  };

  const searchCities = async (query) => {
    if (!query.trim()) {
      setCities([]);
      return;
    }
    try {
      const response = await api.get(`/api/cities?query=${query}`);
      setCities(response.data);
    } catch (error) {
      console.error('Error searching cities:', error);
    }
  };

  const handleSearchChange = (e) => {
    const query = e.target.value;
    setSearchQuery(query);
    searchCities(query);
  };

  const addStop = async (city) => {
    try {
      const newStop = {
        city: { id: city.id },
        arrivalDate: trip.startDate,
        departureDate: trip.endDate,
        orderIndex: stops.length
      };
      await api.post(`/api/trips/${id}/stops`, newStop);
      setShowCitySearch(false);
      setSearchQuery('');
      fetchData();
    } catch (error) {
      console.error('Error adding stop:', error);
      alert('Failed to add stop. Please try again.');
    }
  };

  const deleteStop = async (stopId) => {
    try {
      await api.delete(`/api/trips/${id}/stops/${stopId}`);
      fetchData();
    } catch (error) {
      console.error('Error deleting stop:', error);
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="container">
      <h1>Itinerary Builder: {trip?.name}</h1>
      
      <div className="card">
        <button onClick={() => setShowCitySearch(!showCitySearch)} className="btn btn-primary">
          Add Stop
        </button>

        {showCitySearch && (
          <div className="city-search">
            <input
              type="text"
              placeholder="Search cities..."
              value={searchQuery}
              onChange={handleSearchChange}
              className="form-group input"
            />
            <div className="city-results">
              {cities.map(city => (
                <div key={city.id} className="city-result-item" onClick={() => addStop(city)}>
                  <h4>{city.name}, {city.country}</h4>
                  <p>{city.description}</p>
                </div>
              ))}
            </div>
          </div>
        )}

        <h2>Stops</h2>
        {stops.length === 0 ? (
          <p>No stops added yet. Click "Add Stop" to begin.</p>
        ) : (
          <div className="stops-list">
            {stops.map((stop, index) => (
              <div key={stop.id} className="stop-item">
                <div className="stop-info">
                  <h3>{index + 1}. {stop.city?.name}</h3>
                  <p>Arrival: {new Date(stop.arrivalDate).toLocaleDateString()}</p>
                  <p>Departure: {new Date(stop.departureDate).toLocaleDateString()}</p>
                </div>
                <div className="stop-actions">
                  <Link to={`/activities/${stop.city?.id}`} className="btn btn-secondary">
                    Add Activities
                  </Link>
                  <button onClick={() => deleteStop(stop.id)} className="btn btn-danger">
                    Remove
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>

      <div style={{ marginTop: '20px' }}>
        <Link to={`/trips/${id}/view`} className="btn btn-primary">
          View Itinerary
        </Link>
      </div>
    </div>
  );
};

export default ItineraryBuilder;

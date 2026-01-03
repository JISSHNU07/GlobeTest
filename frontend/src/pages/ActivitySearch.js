import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import api from '../services/api';
import './ActivitySearch.css';

const ActivitySearch = () => {
  const { cityId } = useParams();
  const [activities, setActivities] = useState([]);
  const [city, setCity] = useState(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [filterType, setFilterType] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchData();
  }, [cityId]);

  const fetchData = async () => {
    try {
      const [activitiesRes, cityRes] = await Promise.all([
        api.get(`/api/activities/city/${cityId}`),
        api.get(`/api/cities/${cityId}`)
      ]);
      setActivities(activitiesRes.data);
      setCity(cityRes.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    setLoading(true);
    try {
      const response = await api.get(`/api/activities/city/${cityId}?query=${searchQuery}&type=${filterType}`);
      setActivities(response.data);
    } catch (error) {
      console.error('Error searching activities:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    handleSearch();
  }, [filterType]);

  if (loading && !city) {
    return <div className="loading">Loading...</div>;
  }

  const activityTypes = [
    'SIGHTSEEING', 'FOOD', 'ADVENTURE', 'CULTURE', 'NIGHTLIFE',
    'SHOPPING', 'RELAXATION', 'OUTDOOR', 'MUSEUM', 'EVENT'
  ];

  return (
    <div className="container">
      <h1>Activities in {city?.name}</h1>
      
      <div className="card">
        <div className="filters">
          <input
            type="text"
            placeholder="Search activities..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="search-input"
          />
          <button onClick={handleSearch} className="btn btn-primary">
            Search
          </button>
          <select
            value={filterType}
            onChange={(e) => setFilterType(e.target.value)}
            className="filter-select"
          >
            <option value="">All Types</option>
            {activityTypes.map(type => (
              <option key={type} value={type}>{type}</option>
            ))}
          </select>
        </div>
      </div>

      {loading ? (
        <div className="loading">Loading...</div>
      ) : (
        <div className="activities-grid">
          {activities.map(activity => (
            <div key={activity.id} className="activity-card">
              <h3>{activity.name}</h3>
              <p className="activity-type">{activity.type}</p>
              {activity.description && <p>{activity.description}</p>}
              <div className="activity-details">
                {activity.estimatedCost !== null && (
                  <span>Cost: ${activity.estimatedCost}</span>
                )}
                {activity.durationHours && (
                  <span>Duration: {activity.durationHours}h</span>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ActivitySearch;




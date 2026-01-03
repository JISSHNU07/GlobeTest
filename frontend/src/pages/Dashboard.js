import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';
import './Dashboard.css';

const Dashboard = () => {
  const [trips, setTrips] = useState([]);
  const [popularCities, setPopularCities] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [tripsRes, citiesRes] = await Promise.all([
        api.get('/api/trips'),
        api.get('/api/cities/popular')
      ]);
      setTrips(tripsRes.data);
      setPopularCities(citiesRes.data.slice(0, 6));
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="container">
      <div className="dashboard-header">
        <h1>Welcome to GlobeTrotter!</h1>
        <Link to="/trips/create" className="btn btn-primary">
          Plan New Trip
        </Link>
      </div>

      <div className="dashboard-section">
        <h2>Your Recent Trips</h2>
        {trips.length === 0 ? (
          <div className="empty-state">
            <p>You haven't created any trips yet.</p>
            <Link to="/trips/create" className="btn btn-primary">
              Create Your First Trip
            </Link>
          </div>
        ) : (
          <div className="trip-grid">
            {trips.slice(0, 6).map(trip => (
              <Link key={trip.id} to={`/trips/${trip.id}`} className="trip-card">
                <h3>{trip.name}</h3>
                <p>{trip.description}</p>
                {trip.startDate && trip.endDate && (
                  <p className="trip-dates">
                    {new Date(trip.startDate).toLocaleDateString()} - {new Date(trip.endDate).toLocaleDateString()}
                  </p>
                )}
              </Link>
            ))}
          </div>
        )}
      </div>

      <div className="dashboard-section">
        <h2>Popular Destinations</h2>
        <div className="city-grid">
          {popularCities.map(city => (
            <Link key={city.id} to={`/cities`} className="city-card">
              <h3>{city.name}</h3>
              <p>{city.country}</p>
              <p className="city-info">Cost Index: {city.costIndex}</p>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;




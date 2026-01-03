import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';
import './MyTrips.css';

const MyTrips = () => {
  const [trips, setTrips] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchTrips();
  }, []);

  const fetchTrips = async () => {
    try {
      const response = await api.get('/api/trips');
      setTrips(response.data);
    } catch (error) {
      console.error('Error fetching trips:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this trip?')) {
      return;
    }

    try {
      await api.delete(`/api/trips/${id}`);
      setTrips(trips.filter(trip => trip.id !== id));
    } catch (error) {
      console.error('Error deleting trip:', error);
      alert('Failed to delete trip');
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="container">
      <div className="trips-header">
        <h1>My Trips</h1>
        <Link to="/trips/create" className="btn btn-primary">
          Create New Trip
        </Link>
      </div>

      {trips.length === 0 ? (
        <div className="empty-state">
          <p>You haven't created any trips yet.</p>
          <Link to="/trips/create" className="btn btn-primary">
            Create Your First Trip
          </Link>
        </div>
      ) : (
        <div className="trips-list">
          {trips.map(trip => (
            <div key={trip.id} className="trip-item">
              <div className="trip-info">
                <h3>{trip.name}</h3>
                <p>{trip.description}</p>
                {trip.startDate && trip.endDate && (
                  <p className="trip-dates">
                    {new Date(trip.startDate).toLocaleDateString()} - {new Date(trip.endDate).toLocaleDateString()}
                  </p>
                )}
              </div>
              <div className="trip-actions">
                <Link to={`/trips/${trip.id}`} className="btn btn-secondary">
                  View
                </Link>
                <Link to={`/trips/${trip.id}/itinerary`} className="btn btn-primary">
                  Edit
                </Link>
                <button onClick={() => handleDelete(trip.id)} className="btn btn-danger">
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default MyTrips;




import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import api from '../services/api';

const TripDetails = () => {
  const { id } = useParams();
  const [trip, setTrip] = useState(null);
  const [stops, setStops] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchTripDetails();
  }, [id]);

  const fetchTripDetails = async () => {
    try {
      const [tripRes, stopsRes] = await Promise.all([
        api.get(`/api/trips/${id}`),
        api.get(`/api/trips/${id}/stops`)
      ]);
      setTrip(tripRes.data);
      setStops(stopsRes.data);
    } catch (error) {
      console.error('Error fetching trip details:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (!trip) {
    return <div className="container">Trip not found</div>;
  }

  return (
    <div className="container">
      <h1>{trip.name}</h1>
      <div className="card">
        <p>{trip.description}</p>
        {trip.startDate && trip.endDate && (
          <p>
            <strong>Dates:</strong> {new Date(trip.startDate).toLocaleDateString()} - {new Date(trip.endDate).toLocaleDateString()}
          </p>
        )}
        <div style={{ marginTop: '20px', display: 'flex', gap: '10px' }}>
          <Link to={`/trips/${id}/itinerary`} className="btn btn-primary">
            Edit Itinerary
          </Link>
          <Link to={`/trips/${id}/view`} className="btn btn-secondary">
            View Itinerary
          </Link>
          <Link to={`/trips/${id}/budget`} className="btn btn-secondary">
            Budget
          </Link>
          <Link to={`/trips/${id}/calendar`} className="btn btn-secondary">
            Calendar
          </Link>
        </div>
      </div>

      <div className="card">
        <h2>Stops ({stops.length})</h2>
        {stops.length === 0 ? (
          <p>No stops added yet. <Link to={`/trips/${id}/itinerary`}>Add stops</Link></p>
        ) : (
          <ul>
            {stops.map(stop => (
              <li key={stop.id}>
                {stop.city?.name} - {new Date(stop.arrivalDate).toLocaleDateString()}
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default TripDetails;




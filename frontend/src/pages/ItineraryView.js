import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import api from '../services/api';

const ItineraryView = () => {
  const { id } = useParams();
  const [trip, setTrip] = useState(null);
  const [stops, setStops] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchItinerary();
  }, [id]);

  const fetchItinerary = async () => {
    try {
      const [tripRes, stopsRes] = await Promise.all([
        api.get(`/api/trips/${id}`),
        api.get(`/api/trips/${id}/stops`)
      ]);
      setTrip(tripRes.data);
      setStops(stopsRes.data);
    } catch (error) {
      console.error('Error fetching itinerary:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="container">
      <h1>{trip?.name}</h1>
      <div className="card">
        <h2>Itinerary</h2>
        {stops.length === 0 ? (
          <p>No stops in this itinerary yet.</p>
        ) : (
          <div className="itinerary-timeline">
            {stops.map((stop, index) => (
              <div key={stop.id} className="timeline-item">
                <div className="timeline-marker">{index + 1}</div>
                <div className="timeline-content">
                  <h3>{stop.city?.name}</h3>
                  <p>Arrival: {new Date(stop.arrivalDate).toLocaleDateString()}</p>
                  <p>Departure: {new Date(stop.departureDate).toLocaleDateString()}</p>
                  {stop.activities && stop.activities.length > 0 && (
                    <div className="activities-list">
                      <h4>Activities:</h4>
                      <ul>
                        {stop.activities.map(activity => (
                          <li key={activity.id}>
                            {activity.activity?.name} - {activity.time}
                          </li>
                        ))}
                      </ul>
                    </div>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
      <div style={{ marginTop: '20px' }}>
        <Link to={`/trips/${id}/itinerary`} className="btn btn-primary">
          Edit Itinerary
        </Link>
      </div>
    </div>
  );
};

export default ItineraryView;




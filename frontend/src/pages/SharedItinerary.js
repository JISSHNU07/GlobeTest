import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import api from '../services/api';

const SharedItinerary = () => {
  const { shareToken } = useParams();
  const [trip, setTrip] = useState(null);
  const [stops, setStops] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchSharedTrip();
  }, [shareToken]);

  const fetchSharedTrip = async () => {
    try {
      const tripRes = await api.get(`/api/trips/shared/${shareToken}`);
      setTrip(tripRes.data);
      
      // Fetch stops if trip exists
      if (tripRes.data && tripRes.data.id) {
        try {
          const stopsRes = await api.get(`/api/trips/${tripRes.data.id}/stops`);
          setStops(stopsRes.data);
        } catch (err) {
          // If stops can't be fetched (e.g., private trip), just show trip info
          console.log('Stops not available for this shared trip');
        }
      }
    } catch (error) {
      console.error('Error fetching shared trip:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (!trip) {
    return (
      <div className="container">
        <div className="card">
          <h1>Trip not found</h1>
          <p>This shared trip link is invalid or has expired.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="container">
      <div className="card">
        <h1>{trip.name}</h1>
        {trip.description && <p>{trip.description}</p>}
        {trip.startDate && trip.endDate && (
          <p>
            <strong>Dates:</strong> {new Date(trip.startDate).toLocaleDateString()} - {new Date(trip.endDate).toLocaleDateString()}
          </p>
        )}
        <p className="shared-note">This is a shared itinerary. To create your own, <Link to="/signup">sign up</Link> for GlobeTrotter!</p>
      </div>

      <div className="card">
        <h2>Itinerary</h2>
        {stops.length === 0 ? (
          <p>No stops in this itinerary.</p>
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
                            {activity.time && <strong>{activity.time}</strong>} - {activity.activity?.name}
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
    </div>
  );
};

export default SharedItinerary;

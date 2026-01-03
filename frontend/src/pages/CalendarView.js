import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import api from '../services/api';
import './CalendarView.css';

const CalendarView = () => {
  const { id } = useParams();
  const [trip, setTrip] = useState(null);
  const [stops, setStops] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedDate, setSelectedDate] = useState(null);

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

  const getDatesInRange = (startDate, endDate) => {
    const dates = [];
    const start = new Date(startDate);
    const end = new Date(endDate);
    const current = new Date(start);

    while (current <= end) {
      dates.push(new Date(current));
      current.setDate(current.getDate() + 1);
    }
    return dates;
  };

  const getStopForDate = (date) => {
    const dateStr = date.toISOString().split('T')[0];
    return stops.find(stop => {
      const arrival = new Date(stop.arrivalDate).toISOString().split('T')[0];
      const departure = new Date(stop.departureDate).toISOString().split('T')[0];
      return dateStr >= arrival && dateStr <= departure;
    });
  };

  const getActivitiesForDate = (date) => {
    const stop = getStopForDate(date);
    if (!stop || !stop.activities) return [];
    return stop.activities;
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (!trip || !trip.startDate || !trip.endDate) {
    return <div className="container">Trip dates not set</div>;
  }

  const dates = getDatesInRange(trip.startDate, trip.endDate);

  return (
    <div className="container">
      <h1>Calendar View: {trip.name}</h1>
      
      <div className="calendar-container">
        <div className="calendar-grid">
          {dates.map((date, index) => {
            const stop = getStopForDate(date);
            const activities = getActivitiesForDate(date);
            const isSelected = selectedDate && date.toDateString() === selectedDate.toDateString();
            
            return (
              <div
                key={index}
                className={`calendar-day ${isSelected ? 'selected' : ''}`}
                onClick={() => setSelectedDate(date)}
              >
                <div className="day-header">
                  <div className="day-number">{date.getDate()}</div>
                  <div className="day-name">{date.toLocaleDateString('en-US', { weekday: 'short' })}</div>
                </div>
                {stop && (
                  <div className="day-location">
                    {stop.city?.name}
                  </div>
                )}
                {activities.length > 0 && (
                  <div className="day-activities">
                    {activities.length} activities
                  </div>
                )}
              </div>
            );
          })}
        </div>

        {selectedDate && (
          <div className="day-details">
            <h3>{selectedDate.toLocaleDateString('en-US', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}</h3>
            {(() => {
              const stop = getStopForDate(selectedDate);
              const activities = getActivitiesForDate(selectedDate);
              
              return (
                <>
                  {stop && (
                    <div className="detail-section">
                      <h4>Location: {stop.city?.name}</h4>
                      <p>Arrival: {new Date(stop.arrivalDate).toLocaleDateString()}</p>
                      <p>Departure: {new Date(stop.departureDate).toLocaleDateString()}</p>
                    </div>
                  )}
                  
                  {activities.length > 0 && (
                    <div className="detail-section">
                      <h4>Activities</h4>
                      <ul>
                        {activities.map(activity => (
                          <li key={activity.id}>
                            {activity.time && <strong>{activity.time}</strong>} - {activity.activity?.name}
                            {activity.cost && <span> (${activity.cost})</span>}
                          </li>
                        ))}
                      </ul>
                    </div>
                  )}
                </>
              );
            })()}
          </div>
        )}
      </div>
    </div>
  );
};

export default CalendarView;




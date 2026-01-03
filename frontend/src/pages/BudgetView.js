import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import api from '../services/api';
import './BudgetView.css';

const BudgetView = () => {
  const { id } = useParams();
  const [trip, setTrip] = useState(null);
  const [stops, setStops] = useState([]);
  const [loading, setLoading] = useState(true);
  const [budget, setBudget] = useState({
    total: 0,
    accommodation: 0,
    transport: 0,
    activities: 0,
    meals: 0
  });

  useEffect(() => {
    fetchData();
  }, [id]);

  useEffect(() => {
    if (stops.length > 0) {
      calculateBudget();
    }
  }, [stops]);

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

  const calculateBudget = () => {
    let accommodation = 0;
    let transport = 0;
    let activities = 0;
    const estimatedMeals = 50; // Per day estimate

    stops.forEach(stop => {
      if (stop.accommodationCost) accommodation += stop.accommodationCost;
      if (stop.transportCost) transport += stop.transportCost;
      if (stop.activities) {
        stop.activities.forEach(sa => {
          if (sa.cost) activities += sa.cost;
        });
      }
    });

    const days = stops.reduce((acc, stop) => {
      if (stop.arrivalDate && stop.departureDate) {
        const arrival = new Date(stop.arrivalDate);
        const departure = new Date(stop.departureDate);
        const daysDiff = Math.ceil((departure - arrival) / (1000 * 60 * 60 * 24));
        return acc + daysDiff;
      }
      return acc;
    }, 0);

    const meals = days * estimatedMeals;
    const total = accommodation + transport + activities + meals;

    setBudget({
      total,
      accommodation,
      transport,
      activities,
      meals
    });
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="container">
      <h1>Budget Overview: {trip?.name}</h1>
      
      <div className="card">
        <h2>Total Estimated Cost</h2>
        <div className="total-budget">${budget.total.toFixed(2)}</div>
      </div>

      <div className="card">
        <h2>Cost Breakdown</h2>
        <div className="budget-breakdown">
          <div className="budget-item">
            <span className="budget-label">Accommodation</span>
            <span className="budget-value">${budget.accommodation.toFixed(2)}</span>
          </div>
          <div className="budget-item">
            <span className="budget-label">Transport</span>
            <span className="budget-value">${budget.transport.toFixed(2)}</span>
          </div>
          <div className="budget-item">
            <span className="budget-label">Activities</span>
            <span className="budget-value">${budget.activities.toFixed(2)}</span>
          </div>
          <div className="budget-item">
            <span className="budget-label">Meals (Estimated)</span>
            <span className="budget-value">${budget.meals.toFixed(2)}</span>
          </div>
        </div>

        <div className="budget-chart">
          {budget.total > 0 && (
            <div className="chart-bars">
              {[
                { label: 'Accommodation', value: budget.accommodation, color: '#007bff' },
                { label: 'Transport', value: budget.transport, color: '#28a745' },
                { label: 'Activities', value: budget.activities, color: '#ffc107' },
                { label: 'Meals', value: budget.meals, color: '#dc3545' }
              ].map((item, index) => (
                <div key={index} className="chart-item">
                  <div className="chart-label">{item.label}</div>
                  <div className="chart-bar-container">
                    <div
                      className="chart-bar"
                      style={{
                        width: `${(item.value / budget.total) * 100}%`,
                        backgroundColor: item.color
                      }}
                    />
                  </div>
                  <div className="chart-value">${item.value.toFixed(2)}</div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default BudgetView;


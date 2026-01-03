import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import PrivateRoute from './components/PrivateRoute';
import Login from './pages/Login';
import Signup from './pages/Signup';
import Dashboard from './pages/Dashboard';
import MyTrips from './pages/MyTrips';
import CreateTrip from './pages/CreateTrip';
import TripDetails from './pages/TripDetails';
import ItineraryBuilder from './pages/ItineraryBuilder';
import ItineraryView from './pages/ItineraryView';
import CitySearch from './pages/CitySearch';
import ActivitySearch from './pages/ActivitySearch';
import BudgetView from './pages/BudgetView';
import CalendarView from './pages/CalendarView';
import SharedItinerary from './pages/SharedItinerary';
import Profile from './pages/Profile';
import './App.css';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/shared/:shareToken" element={<SharedItinerary />} />
            
            <Route element={<PrivateRoute />}>
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/trips" element={<MyTrips />} />
              <Route path="/trips/create" element={<CreateTrip />} />
              <Route path="/trips/:id" element={<TripDetails />} />
              <Route path="/trips/:id/itinerary" element={<ItineraryBuilder />} />
              <Route path="/trips/:id/view" element={<ItineraryView />} />
              <Route path="/trips/:id/budget" element={<BudgetView />} />
              <Route path="/trips/:id/calendar" element={<CalendarView />} />
              <Route path="/cities" element={<CitySearch />} />
              <Route path="/activities/:cityId" element={<ActivitySearch />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/" element={<Navigate to="/dashboard" replace />} />
            </Route>
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;


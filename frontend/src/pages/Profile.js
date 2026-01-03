import React, { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../services/api';

const Profile = () => {
  const { user: authUser } = useAuth();
  const [user, setUser] = useState(null);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    photoUrl: ''
  });
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [message, setMessage] = useState('');
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchUser();
  }, []);

  const fetchUser = async () => {
    try {
      setError(null);
      const response = await api.get('/api/users/me');
      if (response.data) {
        setUser(response.data);
        setFormData({
          name: response.data.name || '',
          email: response.data.email || '',
          photoUrl: response.data.photoUrl || ''
        });
      }
    } catch (error) {
      console.error('Error fetching user:', error);
      setError(error.response?.data?.message || error.message || 'Failed to load profile');
      setMessage('Failed to load profile. Please check if the backend is running.');
      // Use authUser as fallback
      if (authUser) {
        setFormData({
          name: authUser.name || '',
          email: authUser.email || '',
          photoUrl: ''
        });
      }
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    setMessage('');

    try {
      await api.put('/api/users/me', formData);
      setMessage('Profile updated successfully!');
      fetchUser();
    } catch (error) {
      setMessage('Failed to update profile. Please try again.');
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return (
      <div className="container">
        <div className="loading">Loading...</div>
      </div>
    );
  }

  return (
    <div className="container" style={{ minHeight: '400px' }}>
      <h1>Profile Settings</h1>
      
      {error && (
        <div className="card" style={{ backgroundColor: '#fff3cd', border: '1px solid #ffc107', padding: '15px', marginBottom: '20px' }}>
          <strong>Warning:</strong> {error}
          <br />
          <small>Make sure the backend is running on http://localhost:8080</small>
        </div>
      )}
      
      {!user && !loading && !error && (
        <div className="card">
          <div className="error">Unable to load profile. Please try refreshing the page.</div>
        </div>
      )}

      {(user || authUser) && (
        <>
          <div className="card">
            <h2>Edit Profile</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>Name</label>
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="form-group">
                <label>Email</label>
                <input
                  type="email"
                  name="email"
                  value={formData.email}
                  disabled
                />
                <small>Email cannot be changed</small>
              </div>
              <div className="form-group">
                <label>Profile Photo URL</label>
                <input
                  type="url"
                  name="photoUrl"
                  value={formData.photoUrl || ''}
                  onChange={handleChange}
                  placeholder="https://example.com/photo.jpg"
                />
              </div>
              {message && (
                <div className={message.includes('success') ? 'success' : 'error'}>
                  {message}
                </div>
              )}
              <button type="submit" className="btn btn-primary" disabled={saving}>
                {saving ? 'Saving...' : 'Save Changes'}
              </button>
            </form>
          </div>

          {user && (
            <div className="card">
              <h2>Account Information</h2>
              {user.createdAt ? (
                <p>Member since: {new Date(user.createdAt).toLocaleDateString()}</p>
              ) : (
                <p>Account information unavailable</p>
              )}
              {user.savedDestinations && user.savedDestinations.length > 0 && (
                <div>
                  <h3>Saved Destinations</h3>
                  <p>{user.savedDestinations.length} saved</p>
                </div>
              )}
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default Profile;


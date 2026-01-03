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

  useEffect(() => {
    fetchUser();
  }, []);

  const fetchUser = async () => {
    try {
      const response = await api.get('/api/users/me');
      setUser(response.data);
      setFormData({
        name: response.data.name || '',
        email: response.data.email || '',
        photoUrl: response.data.photoUrl || ''
      });
    } catch (error) {
      console.error('Error fetching user:', error);
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
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="container">
      <h1>Profile Settings</h1>
      
      <div className="card">
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
              value={formData.photoUrl}
              onChange={handleChange}
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
          <p>Member since: {new Date(user.createdAt).toLocaleDateString()}</p>
          {user.savedDestinations && user.savedDestinations.length > 0 && (
            <div>
              <h3>Saved Destinations</h3>
              <p>{user.savedDestinations.length} saved</p>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default Profile;


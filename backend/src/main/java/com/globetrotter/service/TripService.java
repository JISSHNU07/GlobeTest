package com.globetrotter.service;

import com.globetrotter.model.Trip;
import com.globetrotter.model.User;
import com.globetrotter.repository.TripRepository;
import com.globetrotter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    public Trip createTrip(Trip trip, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        trip.setUser(user);
        return tripRepository.save(trip);
    }

    public Trip updateTrip(Long tripId, Trip tripDetails, Long userId) {
        Trip trip = tripRepository.findByIdAndUserId(tripId, userId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        trip.setName(tripDetails.getName());
        trip.setDescription(tripDetails.getDescription());
        trip.setStartDate(tripDetails.getStartDate());
        trip.setEndDate(tripDetails.getEndDate());
        trip.setCoverPhotoUrl(tripDetails.getCoverPhotoUrl());
        trip.setIsPublic(tripDetails.getIsPublic());

        return tripRepository.save(trip);
    }

    public void deleteTrip(Long tripId, Long userId) {
        Trip trip = tripRepository.findByIdAndUserId(tripId, userId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        tripRepository.delete(trip);
    }

    public List<Trip> getUserTrips(Long userId) {
        List<Trip> trips = tripRepository.findByUserId(userId);
        // Initialize stops to avoid lazy loading issues
        trips.forEach(trip -> {
            if (trip.getStops() != null) {
                trip.getStops().size(); // Force initialization
            }
        });
        return trips;
    }

    public Optional<Trip> getTripById(Long tripId, Long userId) {
        Optional<Trip> trip = tripRepository.findByIdAndUserId(tripId, userId);
        trip.ifPresent(t -> {
            if (t.getStops() != null) {
                t.getStops().size(); // Force initialization
                t.getStops().forEach(stop -> {
                    if (stop.getCity() != null) {
                        stop.getCity().getName(); // Force initialization
                    }
                    if (stop.getActivities() != null) {
                        stop.getActivities().size(); // Force initialization
                    }
                });
            }
        });
        return trip;
    }

    public Optional<Trip> getTripByShareToken(String shareToken) {
        return tripRepository.findByShareToken(shareToken);
    }

    public List<Trip> getPublicTrips() {
        return tripRepository.findPublicTrips();
    }

    public List<com.globetrotter.model.Stop> getStopsByShareToken(String shareToken) {
        Trip trip = tripRepository.findByShareToken(shareToken)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        if (!trip.getIsPublic()) {
            throw new RuntimeException("Trip is not public");
        }
        return trip.getStops();
    }
}

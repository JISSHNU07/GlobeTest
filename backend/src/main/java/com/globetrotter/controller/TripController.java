package com.globetrotter.controller;

import com.globetrotter.model.Trip;
import com.globetrotter.service.TripService;
import com.globetrotter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "http://localhost:3000")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Trip>> getUserTrips() {
        Long userId = userService.getCurrentUserId();
        List<Trip> trips = tripService.getUserTrips(userId);
        return ResponseEntity.ok(trips);
    }

    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) {
        Long userId = userService.getCurrentUserId();
        Trip createdTrip = tripService.createTrip(trip, userId);
        return ResponseEntity.ok(createdTrip);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTrip(@PathVariable Long id) {
        Long userId = userService.getCurrentUserId();
        Optional<Trip> trip = tripService.getTripById(id, userId);
        return trip.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip tripDetails) {
        Long userId = userService.getCurrentUserId();
        try {
            Trip updatedTrip = tripService.updateTrip(id, tripDetails, userId);
            return ResponseEntity.ok(updatedTrip);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrip(@PathVariable Long id) {
        Long userId = userService.getCurrentUserId();
        try {
            tripService.deleteTrip(id, userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/public")
    public ResponseEntity<List<Trip>> getPublicTrips() {
        List<Trip> trips = tripService.getPublicTrips();
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/shared/{shareToken}")
    public ResponseEntity<Trip> getSharedTrip(@PathVariable String shareToken) {
        Optional<Trip> trip = tripService.getTripByShareToken(shareToken);
        return trip.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/shared/{shareToken}/stops")
    public ResponseEntity<List<com.globetrotter.model.Stop>> getSharedTripStops(@PathVariable String shareToken) {
        try {
            List<com.globetrotter.model.Stop> stops = tripService.getStopsByShareToken(shareToken);
            return ResponseEntity.ok(stops);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.globetrotter.controller;

import com.globetrotter.model.Stop;
import com.globetrotter.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips/{tripId}/stops")
@CrossOrigin(origins = "http://localhost:3000")
public class StopController {

    @Autowired
    private StopService stopService;

    @GetMapping
    public ResponseEntity<List<Stop>> getStops(@PathVariable Long tripId) {
        List<Stop> stops = stopService.getStopsByTrip(tripId);
        return ResponseEntity.ok(stops);
    }

    @PostMapping
    public ResponseEntity<Stop> createStop(@PathVariable Long tripId, @RequestBody Stop stop) {
        Stop createdStop = stopService.createStop(stop, tripId);
        return ResponseEntity.ok(createdStop);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stop> updateStop(@PathVariable Long id, @RequestBody Stop stopDetails) {
        try {
            Stop updatedStop = stopService.updateStop(id, stopDetails);
            return ResponseEntity.ok(updatedStop);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStop(@PathVariable Long id) {
        try {
            stopService.deleteStop(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}




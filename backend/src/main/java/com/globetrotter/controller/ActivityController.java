package com.globetrotter.controller;

import com.globetrotter.model.Activity;
import com.globetrotter.model.Activity.ActivityType;
import com.globetrotter.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "http://localhost:3000")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<Activity>> getActivitiesByCity(
            @PathVariable Long cityId,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) ActivityType type,
            @RequestParam(required = false) Double minCost,
            @RequestParam(required = false) Double maxCost) {

        List<Activity> activities;

        if (type != null) {
            activities = activityService.getActivitiesByType(cityId, type);
        } else if (minCost != null && maxCost != null) {
            activities = activityService.getActivitiesByCostRange(cityId, minCost, maxCost);
        } else if (query != null && !query.trim().isEmpty()) {
            activities = activityService.searchActivities(cityId, query);
        } else {
            activities = activityService.getActivitiesByCity(cityId);
        }

        return ResponseEntity.ok(activities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable Long id) {
        try {
            Activity activity = activityService.getActivityById(id);
            return ResponseEntity.ok(activity);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


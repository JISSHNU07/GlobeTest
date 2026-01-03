package com.globetrotter.service;

import com.globetrotter.model.Activity;
import com.globetrotter.model.Activity.ActivityType;
import com.globetrotter.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> getActivitiesByCity(Long cityId) {
        return activityRepository.findByCityId(cityId);
    }

    public List<Activity> searchActivities(Long cityId, String query) {
        if (query == null || query.trim().isEmpty()) {
            return activityRepository.findByCityId(cityId);
        }
        return activityRepository.searchByCityAndQuery(cityId, query);
    }

    public List<Activity> getActivitiesByType(Long cityId, ActivityType type) {
        return activityRepository.findByCityIdAndType(cityId, type);
    }

    public List<Activity> getActivitiesByCostRange(Long cityId, Double minCost, Double maxCost) {
        return activityRepository.findByCityIdAndEstimatedCostBetween(cityId, minCost, maxCost);
    }

    public Activity getActivityById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
    }
}




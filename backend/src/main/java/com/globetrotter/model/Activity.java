package com.globetrotter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activities")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @Column(name = "estimated_cost")
    private Double estimatedCost;

    @Column(name = "duration_hours")
    private Integer durationHours;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StopActivity> stopActivities = new ArrayList<>();

    public Activity() {
        this.stopActivities = new ArrayList<>();
    }

    public Activity(Long id, String name, String description, City city, ActivityType type, Double estimatedCost, Integer durationHours, String imageUrl, List<StopActivity> stopActivities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.type = type;
        this.estimatedCost = estimatedCost;
        this.durationHours = durationHours;
        this.imageUrl = imageUrl;
        this.stopActivities = stopActivities != null ? stopActivities : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<StopActivity> getStopActivities() {
        return stopActivities;
    }

    public void setStopActivities(List<StopActivity> stopActivities) {
        this.stopActivities = stopActivities != null ? stopActivities : new ArrayList<>();
    }

    public enum ActivityType {
        SIGHTSEEING, FOOD, ADVENTURE, CULTURE, NIGHTLIFE, SHOPPING, RELAXATION, OUTDOOR, MUSEUM, EVENT
    }
}

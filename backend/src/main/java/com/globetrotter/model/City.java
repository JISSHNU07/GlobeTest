package com.globetrotter.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cities")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    private String region;

    @Column(name = "cost_index")
    private Double costIndex; // 1-100 scale

    @Column(name = "popularity_score")
    private Integer popularityScore;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Stop> stops = new ArrayList<>();

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Activity> activities = new ArrayList<>();

    public City() {
        this.stops = new ArrayList<>();
        this.activities = new ArrayList<>();
    }

    public City(Long id, String name, String country, String region, Double costIndex, Integer popularityScore, String description, String imageUrl, List<Stop> stops, List<Activity> activities) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.region = region;
        this.costIndex = costIndex;
        this.popularityScore = popularityScore;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stops = stops != null ? stops : new ArrayList<>();
        this.activities = activities != null ? activities : new ArrayList<>();
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Double getCostIndex() {
        return costIndex;
    }

    public void setCostIndex(Double costIndex) {
        this.costIndex = costIndex;
    }

    public Integer getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(Integer popularityScore) {
        this.popularityScore = popularityScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops != null ? stops : new ArrayList<>();
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities != null ? activities : new ArrayList<>();
    }
}

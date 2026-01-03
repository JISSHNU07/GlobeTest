package com.globetrotter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String name;

    private String photoUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> trips = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_saved_destinations", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "city_id")
    private List<Long> savedDestinations = new ArrayList<>();

    public User() {
        this.trips = new ArrayList<>();
        this.savedDestinations = new ArrayList<>();
    }

    public User(Long id, String email, String password, String name, String photoUrl, LocalDateTime createdAt, LocalDateTime updatedAt, List<Trip> trips, List<Long> savedDestinations) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.photoUrl = photoUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.trips = trips != null ? trips : new ArrayList<>();
        this.savedDestinations = savedDestinations != null ? savedDestinations : new ArrayList<>();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips != null ? trips : new ArrayList<>();
    }

    public List<Long> getSavedDestinations() {
        return savedDestinations;
    }

    public void setSavedDestinations(List<Long> savedDestinations) {
        this.savedDestinations = savedDestinations != null ? savedDestinations : new ArrayList<>();
    }
}

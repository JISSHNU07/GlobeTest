package com.globetrotter.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stops")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonBackReference
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    @JsonBackReference
    private City city;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "order_index")
    private Integer orderIndex;

    @OneToMany(mappedBy = "stop", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("time ASC")
    @JsonBackReference
    private List<StopActivity> activities = new ArrayList<>();

    @Column(name = "accommodation_cost")
    private Double accommodationCost;

    @Column(name = "transport_cost")
    private Double transportCost;

    public Stop() {
        this.activities = new ArrayList<>();
    }

    public Stop(Long id, Trip trip, City city, LocalDate arrivalDate, LocalDate departureDate, Integer orderIndex, List<StopActivity> activities, Double accommodationCost, Double transportCost) {
        this.id = id;
        this.trip = trip;
        this.city = city;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.orderIndex = orderIndex;
        this.activities = activities != null ? activities : new ArrayList<>();
        this.accommodationCost = accommodationCost;
        this.transportCost = transportCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public List<StopActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<StopActivity> activities) {
        this.activities = activities != null ? activities : new ArrayList<>();
    }

    public Double getAccommodationCost() {
        return accommodationCost;
    }

    public void setAccommodationCost(Double accommodationCost) {
        this.accommodationCost = accommodationCost;
    }

    public Double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(Double transportCost) {
        this.transportCost = transportCost;
    }
}

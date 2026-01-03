package com.globetrotter.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "stop_activities")
public class StopActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stop_id", nullable = false)
    private Stop stop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    private LocalTime time;

    private Double cost;

    private String notes;

    public StopActivity() {
    }

    public StopActivity(Long id, Stop stop, Activity activity, LocalTime time, Double cost, String notes) {
        this.id = id;
        this.stop = stop;
        this.activity = activity;
        this.time = time;
        this.cost = cost;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}


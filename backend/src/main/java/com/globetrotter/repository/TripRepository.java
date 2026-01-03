package com.globetrotter.repository;

import com.globetrotter.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUserId(Long userId);
    Optional<Trip> findByShareToken(String shareToken);
    Optional<Trip> findByIdAndUserId(Long id, Long userId);
    
    @Query("SELECT t FROM Trip t WHERE t.isPublic = true ORDER BY t.createdAt DESC")
    List<Trip> findPublicTrips();
}


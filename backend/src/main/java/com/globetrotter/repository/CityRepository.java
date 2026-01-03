package com.globetrotter.repository;

import com.globetrotter.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByNameContainingIgnoreCase(String name);
    List<City> findByCountryIgnoreCase(String country);
    
    @Query("SELECT c FROM City c WHERE " +
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.country) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<City> searchByNameOrCountry(@Param("query") String query);
    
    @Query("SELECT c FROM City c ORDER BY c.popularityScore DESC")
    List<City> findPopularCities();
}


package com.globetrotter.repository;

import com.globetrotter.model.Activity;
import com.globetrotter.model.Activity.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByCityId(Long cityId);
    List<Activity> findByCityIdAndType(Long cityId, ActivityType type);
    
    @Query("SELECT a FROM Activity a WHERE a.city.id = :cityId AND " +
           "(LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(a.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Activity> searchByCityAndQuery(@Param("cityId") Long cityId, @Param("query") String query);
    
    List<Activity> findByCityIdAndEstimatedCostBetween(Long cityId, Double minCost, Double maxCost);
}




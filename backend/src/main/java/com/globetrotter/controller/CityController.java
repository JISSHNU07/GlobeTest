package com.globetrotter.controller;

import com.globetrotter.model.City;
import com.globetrotter.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@CrossOrigin(origins = "http://localhost:3000")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> searchCities(@RequestParam(required = false) String query) {
        List<City> cities = cityService.searchCities(query);
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<City>> getPopularCities() {
        List<City> cities = cityService.getPopularCities();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<City>> getCitiesByCountry(@PathVariable String country) {
        List<City> cities = cityService.getCitiesByCountry(country);
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable Long id) {
        try {
            City city = cityService.getCityById(id);
            return ResponseEntity.ok(city);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}




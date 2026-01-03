package com.globetrotter.service;

import com.globetrotter.model.City;
import com.globetrotter.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> searchCities(String query) {
        if (query == null || query.trim().isEmpty()) {
            return cityRepository.findPopularCities();
        }
        return cityRepository.searchByNameOrCountry(query);
    }

    public List<City> getPopularCities() {
        return cityRepository.findPopularCities();
    }

    public List<City> getCitiesByCountry(String country) {
        return cityRepository.findByCountryIgnoreCase(country);
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}




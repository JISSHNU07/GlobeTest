package com.globetrotter.service;

import com.globetrotter.model.City;
import com.globetrotter.model.Stop;
import com.globetrotter.model.Trip;
import com.globetrotter.repository.CityRepository;
import com.globetrotter.repository.StopRepository;
import com.globetrotter.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StopService {

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CityRepository cityRepository;

    public Stop createStop(Stop stop, Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        stop.setTrip(trip);
        
        // Handle city reference - if city has an id, fetch it from repository
        if (stop.getCity() != null && stop.getCity().getId() != null) {
            City city = cityRepository.findById(stop.getCity().getId())
                    .orElseThrow(() -> new RuntimeException("City not found"));
            stop.setCity(city);
        }
        
        return stopRepository.save(stop);
    }

    public Stop updateStop(Long stopId, Stop stopDetails) {
        Stop stop = stopRepository.findById(stopId)
                .orElseThrow(() -> new RuntimeException("Stop not found"));

        // Handle city reference update
        if (stopDetails.getCity() != null && stopDetails.getCity().getId() != null) {
            City city = cityRepository.findById(stopDetails.getCity().getId())
                    .orElseThrow(() -> new RuntimeException("City not found"));
            stop.setCity(city);
        }
        
        stop.setArrivalDate(stopDetails.getArrivalDate());
        stop.setDepartureDate(stopDetails.getDepartureDate());
        stop.setOrderIndex(stopDetails.getOrderIndex());
        stop.setAccommodationCost(stopDetails.getAccommodationCost());
        stop.setTransportCost(stopDetails.getTransportCost());

        return stopRepository.save(stop);
    }

    public void deleteStop(Long stopId) {
        stopRepository.deleteById(stopId);
    }

    public List<Stop> getStopsByTrip(Long tripId) {
        return stopRepository.findByTripId(tripId);
    }
}

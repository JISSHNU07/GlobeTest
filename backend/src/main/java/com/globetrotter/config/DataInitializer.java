package com.globetrotter.config;

import com.globetrotter.model.Activity;
import com.globetrotter.model.City;
import com.globetrotter.repository.ActivityRepository;
import com.globetrotter.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public void run(String... args) throws Exception {
        if (cityRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // Cities
        City paris = createCity("Paris", "France", "Europe", 75.0, 95, "The City of Light", null);
        City tokyo = createCity("Tokyo", "Japan", "Asia", 85.0, 90, "Modern metropolis with ancient traditions", null);
        City newYork = createCity("New York", "USA", "North America", 90.0, 92, "The Big Apple", null);
        City london = createCity("London", "United Kingdom", "Europe", 80.0, 88, "Historic capital city", null);
        City sydney = createCity("Sydney", "Australia", "Oceania", 70.0, 85, "Harbor city", null);

        cityRepository.save(paris);
        cityRepository.save(tokyo);
        cityRepository.save(newYork);
        cityRepository.save(london);
        cityRepository.save(sydney);

        // Activities for Paris
        activityRepository.save(createActivity("Eiffel Tower", "Iconic iron lattice tower", paris, Activity.ActivityType.SIGHTSEEING, 25.0, 2));
        activityRepository.save(createActivity("Louvre Museum", "World's largest art museum", paris, Activity.ActivityType.MUSEUM, 17.0, 4));
        activityRepository.save(createActivity("Seine River Cruise", "Scenic boat tour", paris, Activity.ActivityType.SIGHTSEEING, 15.0, 1));
        activityRepository.save(createActivity("French Cooking Class", "Learn French cuisine", paris, Activity.ActivityType.FOOD, 80.0, 3));

        // Activities for Tokyo
        activityRepository.save(createActivity("Senso-ji Temple", "Ancient Buddhist temple", tokyo, Activity.ActivityType.CULTURE, 0.0, 2));
        activityRepository.save(createActivity("Tokyo Skytree", "Tallest structure in Japan", tokyo, Activity.ActivityType.SIGHTSEEING, 20.0, 2));
        activityRepository.save(createActivity("Sushi Making Class", "Learn authentic sushi", tokyo, Activity.ActivityType.FOOD, 100.0, 3));
        activityRepository.save(createActivity("Shibuya Crossing", "World's busiest intersection", tokyo, Activity.ActivityType.SIGHTSEEING, 0.0, 1));

        // Activities for New York
        activityRepository.save(createActivity("Statue of Liberty", "Iconic symbol of freedom", newYork, Activity.ActivityType.SIGHTSEEING, 24.0, 3));
        activityRepository.save(createActivity("Central Park", "Urban park in Manhattan", newYork, Activity.ActivityType.OUTDOOR, 0.0, 3));
        activityRepository.save(createActivity("Broadway Show", "World-famous theater", newYork, Activity.ActivityType.EVENT, 100.0, 3));
        activityRepository.save(createActivity("Empire State Building", "Art Deco skyscraper", newYork, Activity.ActivityType.SIGHTSEEING, 42.0, 2));

        // Activities for London
        activityRepository.save(createActivity("Big Ben", "Famous clock tower", london, Activity.ActivityType.SIGHTSEEING, 0.0, 1));
        activityRepository.save(createActivity("British Museum", "World history museum", london, Activity.ActivityType.MUSEUM, 0.0, 3));
        activityRepository.save(createActivity("London Eye", "Giant observation wheel", london, Activity.ActivityType.SIGHTSEEING, 32.0, 1));
        activityRepository.save(createActivity("Afternoon Tea", "Traditional British experience", london, Activity.ActivityType.FOOD, 45.0, 2));

        // Activities for Sydney
        activityRepository.save(createActivity("Sydney Opera House", "Iconic performing arts center", sydney, Activity.ActivityType.CULTURE, 43.0, 2));
        activityRepository.save(createActivity("Sydney Harbour Bridge", "Steel arch bridge", sydney, Activity.ActivityType.OUTDOOR, 268.0, 3));
        activityRepository.save(createActivity("Bondi Beach", "Famous beach", sydney, Activity.ActivityType.RELAXATION, 0.0, 4));
        activityRepository.save(createActivity("Taronga Zoo", "Wildlife park", sydney, Activity.ActivityType.OUTDOOR, 47.0, 4));
    }

    private City createCity(String name, String country, String region, Double costIndex, Integer popularity, String description, String imageUrl) {
        City city = new City();
        city.setName(name);
        city.setCountry(country);
        city.setRegion(region);
        city.setCostIndex(costIndex);
        city.setPopularityScore(popularity);
        city.setDescription(description);
        city.setImageUrl(imageUrl);
        return city;
    }

    private Activity createActivity(String name, String description, City city, Activity.ActivityType type, Double cost, Integer duration) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        activity.setCity(city);
        activity.setType(type);
        activity.setEstimatedCost(cost);
        activity.setDurationHours(duration);
        return activity;
    }
}




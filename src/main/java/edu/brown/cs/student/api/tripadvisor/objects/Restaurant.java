package edu.brown.cs.student.api.tripadvisor.objects;

import edu.brown.cs.student.api.tripadvisor.util.GeoLocation;

/**
 * A restaurant, as defined by the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class Restaurant {
    private String name;
    // location ID is defined by TripAdvisor API
    private String location_id;
    private GeoLocation geoLocation;
    // e.g. hours[0][1] = opening time on Monday
    private String[][] hours;
    private String websiteURL;
    private String priceLevel;
    private String rating;
    // e.g. #1 of 1,201 restaurants in New York City
    private String ranking;
}

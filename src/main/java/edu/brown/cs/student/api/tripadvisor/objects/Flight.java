package api.tripadvisor.objects;

import api.tripadvisor.util.GeoLocation;

/**
 * A flight, as defined by the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class Flight {
    private String origin;
    private String destination;
    private String departureDate;
    private String carrier;
    private String price;
    private CabinClass cabinClass;
    private Stop stops;
    private GeoLocation geoLocation;
    // Score is defined by TripAdvisor API.
    private FlightScore flightScore;
    private String searchURL;

    public enum CabinClass {
        ECONOMY, BUSINESS, FIRST, PREMIUM
    }

    public enum FlightScore {

    }

    public enum Stop {
        NONE, ONE, MULTIPLE
    }
}

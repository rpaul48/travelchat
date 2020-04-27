package api.tripadvisor.response;

import api.tripadvisor.objects.Flight;

import java.util.List;

/**
 * A response for flights from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class FlightResponse {
    private List<Flight> flights;

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}

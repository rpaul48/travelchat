package api.tripadvisor.request;

import java.util.Map;

/**
 * A request for flights from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class FlightRequest extends Request{
    public FlightRequest(String hostURL, String currency, Map<String, Object> givenParams) {
        super(hostURL, currency, givenParams);
    }
}

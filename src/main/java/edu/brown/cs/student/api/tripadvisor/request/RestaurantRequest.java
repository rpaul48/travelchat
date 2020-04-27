package api.tripadvisor.request;

import java.util.Map;

/**
 * A request for restaurants from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class RestaurantRequest extends Request {
    public RestaurantRequest(String hostURL, String currency, Map<String, Object> givenParams) {
        super(hostURL, currency, givenParams);
    }
}

package api.tripadvisor.response;

import api.tripadvisor.objects.Restaurant;

import java.util.List;

/**
 * A response for restaurants from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class RestaurantResponse {
    private List<Restaurant> restaurants;

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}

package edu.brown.cs.student.api.tripadvisor.response;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * A response for restaurants from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class RestaurantResponse {
    private List<Restaurant> restaurants;

    public RestaurantResponse(HttpResponse<JsonNode> response) {
        this.restaurants = new ArrayList<>();
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

}

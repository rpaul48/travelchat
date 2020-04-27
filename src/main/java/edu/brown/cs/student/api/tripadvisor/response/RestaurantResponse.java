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
public class RestaurantResponse extends Response {
    private List<Restaurant> restaurants;

    public RestaurantResponse(HttpResponse<JsonNode> response) {
        this.restaurants = new ArrayList<>();
        this.parseResponse(response);
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public void parseResponse(HttpResponse<JsonNode> response) {
        //TODO: Specific parsing logic.
    }
}

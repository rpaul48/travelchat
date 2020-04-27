package edu.brown.cs.student.api.tripadvisor.response;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import edu.brown.cs.student.api.tripadvisor.objects.Hotel;

import java.util.ArrayList;
import java.util.List;

/**
 * A response for hotels from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class HotelResponse extends Response {
    private List<Hotel> hotels;

    public HotelResponse(HttpResponse<JsonNode> response) {
        this.hotels = new ArrayList<>();
        this.parseResponse(response);
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    @Override
    public void parseResponse(HttpResponse<JsonNode> response) {
        //TODO: Specific parsing logic.
    }
}

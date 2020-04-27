package api.tripadvisor.response;

import api.tripadvisor.objects.Hotel;

import java.util.List;

/**
 * A response for hotels from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class HotelResponse {
    private List<Hotel> hotels;

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}

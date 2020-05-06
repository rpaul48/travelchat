package edu.brown.cs.student.api.tripadvisor.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Hotel;
import edu.brown.cs.student.api.tripadvisor.request.HotelRequest;

/**
 * A response for hotels from the TripAdvisor API.
 */
public class HotelResponse {
  private HotelRequest hotelRequest;

  /**
   * Constructs a Response object built on given request data.
   *
   * @param hotelRequest The data we'll use in our parsers.
   */
  public HotelResponse(HotelRequest hotelRequest) {
    this.hotelRequest = hotelRequest;
  }

  /**
   * Getter of hotel request.
   *
   * @return hotel request.
   */
  public HotelRequest gethotelRequest() {
    return hotelRequest;
  }

  /**
   * Setter of hotel request.
   *
   * @param hotelRequest to newly set to.
   */
  public void setHotelRequest(HotelRequest hotelRequest) {
    this.hotelRequest = hotelRequest;
  }

  /**
   * Parses all relevant fields from the raw HTTP response for Hotel.
   *
   * @return List of hotels matching query parameters.
   * @throws UnirestException - thrown if JSONArray mapped by "data" cannot be
   *                          obtained.
   */
  public List<Hotel> getData() throws UnirestException {
    List<Hotel> hotelsList = new ArrayList<>();

    try {
      String queryResult = hotelRequest.run();

      if (queryResult.equals("")) {
        return hotelsList;
      }

      JSONObject obj = new JSONObject(queryResult);
      JSONArray hotelsArr = (JSONArray) obj.get("data");
      // goes through all of the hotels recommended
      for (int i = 0; i < hotelsArr.length(); i++) {
        Hotel hotel = new Hotel();
        JSONObject hotelObj = (JSONObject) hotelsArr.get(i);

        try {
          JSONObject photoObj = (JSONObject) hotelObj.get("photo");
          JSONObject imagesObj = (JSONObject) photoObj.get("images");
          JSONObject smallObj = (JSONObject) imagesObj.get("small");

          hotel.setPhotoUrl(smallObj.getString("url"));
          hotel.setName(hotelObj.getString("name"));
          hotel.setLatitude(hotelObj.getDouble("latitude"));
          hotel.setLongitude(hotelObj.getDouble("longitude"));
          hotel.setDistance(hotelObj.getDouble("distance"));
          hotel.setNumReviews(hotelObj.getInt("num_reviews"));
          hotel.setLocationString(hotelObj.getString("location_string"));
          hotel.setRating(hotelObj.getDouble("rating"));
          hotel.setPriceLevel(hotelObj.getString("price_level"));
          hotel.setPrice(hotelObj.getString("price"));
          hotel.setRanking(hotelObj.getInt("ranking_position"));
          hotel.setRankingString(hotelObj.getString("ranking"));
          hotel.setClosed(hotelObj.getBoolean("is_closed"));
          hotel.setHotelClass(hotelObj.getString("hotel_class"));
        } catch (org.json.JSONException exception) {
          continue;
        }

        hotelsList.add(hotel);
      }
    } catch (org.json.JSONException exception) {
      System.err.println("ERROR: Data is unobtainable.");
    }

    return hotelsList;
  }
}

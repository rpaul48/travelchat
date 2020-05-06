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
          if (hotelObj.isNull("name")) {
            continue;
          }
          hotel.setName(hotelObj.getString("name"));

          if (!hotelObj.isNull("latitude") && !hotelObj.isNull("longitude")) {
            hotel.setLatitude(hotelObj.getDouble("latitude"));
            hotel.setLongitude(hotelObj.getDouble("longitude"));
          }

          if (!hotelObj.isNull("distance")) {
            hotel.setDistance(hotelObj.getDouble("distance"));
          }

          if (!hotelObj.isNull("num_reviews")) {
            hotel.setNumReviews(hotelObj.getInt("num_reviews"));
          }

          if (!hotelObj.isNull("location_string")) {
            hotel.setLocationString(hotelObj.getString("location_string"));
          }

          if (!hotelObj.isNull("rating")) {
            hotel.setRating(hotelObj.getDouble("rating"));
          }

          if (!hotelObj.isNull("price_level")) {
            hotel.setPriceLevel(hotelObj.getString("price_level"));
          }

          if (!hotelObj.isNull("price")) {
            hotel.setPriceLevel(hotelObj.getString("price"));
          }

          if (!hotelObj.isNull("ranking")) {
            hotel.setRankingString(hotelObj.getString("ranking"));
          }

          if (!hotelObj.isNull("ranking_position")) {
            hotel.setRanking(hotelObj.getInt("ranking_position"));
          }

          if (!hotelObj.isNull("is_closed")) {
            hotel.setClosed(hotelObj.getBoolean("is_closed"));
          }

          if (!hotelObj.isNull("hotel_class")) {
            hotel.setHotelClass(hotelObj.getDouble("hotel_class"));
          }

          if (!hotelObj.isNull("photo")) {
            JSONObject photoObj = (JSONObject) hotelObj.get("photo");
            if (!photoObj.isNull("images")) {
              JSONObject imagesObj = (JSONObject) photoObj.get("images");
              if (!imagesObj.isNull("small")) {
                JSONObject smallObj = (JSONObject) imagesObj.get("small");
                if (!smallObj.isNull("url")) {
                  hotel.setPhotoUrl(smallObj.getString("url"));
                }
              }
            }
          }
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

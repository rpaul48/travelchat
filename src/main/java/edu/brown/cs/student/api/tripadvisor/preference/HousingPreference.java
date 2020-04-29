package edu.brown.cs.student.api.tripadvisor.preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Hotel;
import edu.brown.cs.student.api.tripadvisor.objects.Item;

public class HousingPreference implements Preference {
  private double rating; // unused in query
  private double pricesmax;
  private double latitude;
  private double longitude;
  private int rooms; // number of rooms
  private List<String> amenities;
  private String checkin; // yyyy-mm-dd
  private int nights; // number of nights to stay
  // can add more fields if needed
  private Map<String, Object> fields;

  public HousingPreference() {
    fields = new HashMap<>();
  }

  public HousingPreference(Map<String, Object> fields) {
    this.fields = fields;
  }

  public HousingPreference(double latitude, double longitude, double rating, double pricesmax,
      int rooms, List<String> amenities, String checkin, int nights) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.rating = rating;
    this.pricesmax = pricesmax;
    this.rooms = rooms;
    this.amenities = amenities;
    this.checkin = checkin;
    this.nights = nights;
    this.buildFields();
  }

  @Override
  public void buildFields() {
    String strAmenities = "";
    if (this.amenities != null) {
      for (int i = 0; i < this.amenities.size() - 1; i++) {
        strAmenities += this.amenities.get(i);
        strAmenities += ",";
      }
      strAmenities += this.amenities.get(this.amenities.size() - 1);
    }

    fields = new HashMap<>();
    fields.put("limit", 10); // adjust
    fields.put("lang", "en_US"); // adjust
    fields.put("currency", "USD"); // adjust
    fields.put("latitude", latitude);
    fields.put("longitude", longitude);
    fields.put("rooms", rooms);
    fields.put("amenities", strAmenities);
    fields.put("pricesmax", pricesmax);
    fields.put("checkin", checkin);
    fields.put("nights", nights);
  }

  @Override
  public String getQueryString() throws UnirestException {
    // Latitude and longitude are required parameters. Query cannot be run without
    // them.
    if (!fields.containsKey("latitude") || !fields.containsKey("longitude")) {
      System.err.println("ERROR: Latitude or longitude missing.");
      return null;
    }

    // If fields is passed directly into constructor, latitude and longitude might
    // not have been assigned.
    latitude = (double) fields.get("latitude");
    longitude = (double) fields.get("longitude");

    if (!(latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180)) {
      System.err.println("ERROR: Latitude or longitude invalid");
      return null;
    }

    HttpResponse<String> response = Unirest
        .get("https://tripadvisor1.p.rapidapi.com/hotels/list-by-latlng?")
        .header("x-rapidapi-host", "tripadvisor1.p.rapidapi.com")
        .header("x-rapidapi-key", "9ab9c1d3bdmsha453182e940dd58p105f14jsna2fade8f7b4d")
        .queryString(fields).asString();
    return response.getBody();
  }

  @Override
  public List<Item> parseResult() throws JSONException, UnirestException {
    List<Item> hotelsList = new ArrayList<Item>();

    try {
      JSONObject obj = new JSONObject(this.getQueryString());
      JSONArray hotelsArr = (JSONArray) obj.get("data");
      // goes through all of the hotels recommended
      for (int i = 0; i < hotelsArr.length(); i++) {
        Hotel hotel = new Hotel();
        JSONObject hotelObj = (JSONObject) hotelsArr.get(i);

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

        hotelsList.add(hotel);
      }
    } catch (org.json.JSONException exception) {
      System.err.println("ERROR: Missing element in API result causing error in parsing.");
    }

    return hotelsList;
  }

  public double getRating() {
    return rating;
  }

  public double getpricesmax() {
    return pricesmax;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public void setPricesmax(double pricesmax) {
    this.pricesmax = pricesmax;
  }

  public double getPricesmax() {
    return pricesmax;
  }

  @Override
  public double getLatitude() {
    return latitude;
  }

  @Override
  public double getLongitude() {
    return longitude;
  }

  public int getRooms() {
    return rooms;
  }

  public List<String> getAmenities() {
    return amenities;
  }

  public String getCheckin() {
    return checkin;
  }

  @Override
  public Map<String, Object> getFields() {
    return fields;
  }

  public int getNights() {
    return nights;
  }

  @Override
  public void setLatitude(double latitude) {
    this.latitude = latitude;
    fields.put("latitude", latitude);
  }

  @Override
  public void setLongitude(double longitude) {
    this.longitude = longitude;
    fields.put("longitude", longitude);
  }

  public void setRooms(int rooms) {
    this.rooms = rooms;
    fields.put("rooms", rooms);
  }

  public void setAmenities(List<String> amenities) {
    this.amenities = amenities;
    String strAmenities = "";
    if (this.amenities != null) {
      for (int i = 0; i < this.amenities.size() - 1; i++) {
        strAmenities += this.amenities.get(i);
        strAmenities += ",";
      }
      strAmenities += this.amenities.get(this.amenities.size() - 1);
    }
    fields.put("amenities", strAmenities);
  }

  public void setCheckin(String checkin) {
    this.checkin = checkin;
    fields.put("checkin", checkin);
  }

  @Override
  public void setFields(Map<String, Object> fields) {
    this.fields = fields;
  }

  public void setNights(int nights) {
    this.nights = nights;
    fields.put("nights", nights);
  }
}

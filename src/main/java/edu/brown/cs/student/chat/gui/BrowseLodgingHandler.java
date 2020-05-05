package edu.brown.cs.student.chat.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.student.api.tripadvisor.objects.Hotel;
import edu.brown.cs.student.api.tripadvisor.querier.TripAdvisorQuerier;
import edu.brown.cs.student.api.tripadvisor.request.HotelRequest;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class BrowseLodgingHandler implements Route {
  @Override
  public JSONObject handle(Request request, Response response) throws Exception {
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    QueryParamsMap qm = request.queryMap();
    String errorMsg = "";

    // of the form "[lat], [lon]"
    String[] locationStrings = qm.value("location").split(",");
    String lat = locationStrings[0];
    String lon = locationStrings[1];

    // options: "Any", "Hotel", "Bed and breakfast", "Specialty"
    String subcategory = qm.value("type").toLowerCase();
    if (subcategory.equals("any")) {
      subcategory = "all";
    } else if (subcategory.contains("bed and breakfast")) {
      subcategory = subcategory.replace("bed and breakfast", "bb");
    }

    // format: "2020-05-15" (year-month in two digits-day number in two digits)
    String checkIn = qm.value("check-in");
    String checkOut = qm.value("check-out");
    String nights = "";
    try {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate checkInDate = LocalDate.parse(checkIn, dtf);
      LocalDate checkOutDate = LocalDate.parse(checkOut, dtf);
      nights = String.valueOf(ChronoUnit.DAYS.between(checkInDate, checkOutDate));
    } catch (DateTimeParseException e) {
      errorMsg = "ERROR: Invalid date format.";
      System.out.println(errorMsg);
      Map<String, String> variables = ImmutableMap.of("restaurants_result", "",
          "restaurants_errors", errorMsg);
      return new JSONObject(variables);
    }

    // min rating; options: "Any", "1 star", "2 star", "3 star", "4 star", or "5
    // star"
    String hotelClass = qm.value("rating").replaceAll("[^1-5,]", "");
    if (hotelClass.equals("")) {
      hotelClass = "all";
    }

    // format: integer
    String numRooms = qm.value("num-rooms");

    Map<String, Object> params = new HashMap<>();
    params.put("limit", Constants.LIMIT);
    params.put("lang", Constants.LANG);
    params.put("currency", Constants.CURRENCY);
    params.put("lunit", Constants.LUNIT);
    params.put("latitude", lat);
    params.put("longitude", lon);
    params.put("checkin", checkIn);
    params.put("subcategory", subcategory);
    params.put("nights", nights);
    params.put("rooms", numRooms);
    params.put("hotel_class", hotelClass);

    errorMsg = paramsAreValid(params);
    // Parameters are invalid.
    if (!errorMsg.equals("")) {
      System.out.println(errorMsg);
      Map<String, String> variables = ImmutableMap.of("restaurants_result", "",
          "restaurants_errors", errorMsg);
      return new JSONObject(variables);
    }

    List<Hotel> hotels = querier.getHotels(new HotelRequest(params));

    StringBuilder sb = new StringBuilder();
    if (hotels.isEmpty()) {
      sb.append("No matching result.");
    } else {
      for (Hotel hotel : hotels) {
        sb.append(hotel.toString()).append("\n-----------------------------\n");
      }
    }

    Map<String, String> variables = ImmutableMap.of("lodging_result", sb.toString(),
        "lodging_errors", "");
    return new JSONObject(variables);
  }

  /**
   * Checks if each element in the params is valid and is in the correct
   * type/format.
   *
   * @param params parameters
   * @return "" if all params are valid, error messages otherwise
   */
  public String paramsAreValid(Map<String, Object> params) {
    // Latitude and longitude are required parameters. Query cannot be run without
    // them.
    if (!params.containsKey("latitude") || !params.containsKey("longitude")) {
      return "ERROR: Latitude or longitude is missing.";
    }

    double latitude;
    double longitude;
    try {
      latitude = Double.parseDouble((String) params.get("latitude"));
      longitude = Double.parseDouble((String) params.get("longitude"));
    } catch (NumberFormatException nfe) {
      return "ERROR: Latitude or longitude is not a number.";
    }

    if (!(latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180)) {
      return "ERROR: Latitude or longitude is out of range.";
    }

    try {
      if (Integer.parseInt((String) params.get("rooms")) < 0) {
        return "ERROR: Number of rooms cannot be negative.";
      }
    } catch (NumberFormatException nfe) {
      return "ERROR: Number of rooms should be a number.";
    }

    return "";
  }
}

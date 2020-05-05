package edu.brown.cs.student.chat.gui;

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

    // of the form "[lat], [lon]"
    String[] locationStrings = qm.value("location").split(",");
    String lat = locationStrings[0].replaceAll("[^0-9.]", "");
    String lon = locationStrings[1].replaceAll("[^0-9.]", "");

    // options: "Any", "Hotel", "Bed and breakfast", "Specialty"
    String subcategory = qm.value("type").toLowerCase();
    if (subcategory.equals("any")) {
      subcategory = "";
    } else if (subcategory.contains("bed and breakfast")) {
      subcategory.replace("bed and breakfast", "bb");
    }

    // format: "2020-05-15" (year-month in two digits-day number in two digits)
    String checkIn = qm.value("check-in");

    // format: integer
    String numNights = qm.value("num-nights");/////// *** CHANGED!

    // min rating; options: "Any", "1 star", "2 star", "3 star", "4 star", or "5
    // star"
    String hotelClass = qm.value("rating").replaceAll("[^0-9,.]", "");

    // format: double
    String minPrice = qm.value("min-price");/////// *** CHANGED!
    String maxPrice = qm.value("max-price");/////// *** CHANGED!

    // format: integer
    String numRooms = qm.value("num-rooms");

    Map<String, Object> params = new HashMap<>();
    params.put("limit", Constants.LIMIT); // adjust
    params.put("lang", Constants.LANG); // adjust
    params.put("currency", Constants.CURRENCY); // adjust
    params.put("lunit", Constants.LUNIT); // adjust
    params.put("latitude", lat);
    params.put("longitude", lon);
    params.put("checkin", checkIn);
    params.put("subcategory", subcategory);
    params.put("nights", numNights);
    params.put("rooms", numRooms);
    params.put("hotel_class", hotelClass);
    params.put("pricesmin", minPrice);
    params.put("pricesmax", maxPrice);

    String errorMsg = paramsAreValid(params);
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
        sb.append(hotel.toString() + "\n-----------------------------\n");
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
   * @param Map<String, Object> params
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

    // Format: YYYY-MM-DD
    String date_format = "^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$";
    if (!((String) params.get("checkin")).matches(date_format)) {
      return "ERROR: The check-in date is not in correct format.";
    }

    try {
      if (Integer.parseInt((String) params.get("nights")) < 0) {
        return "ERROR: The number of nights to stay cannot be negative.";
      }

      if (Integer.parseInt((String) params.get("rooms")) < 0) {
        return "ERROR: The number of rooms cannot be negative.";
      }

      double pricesmin = Double.parseDouble((String) params.get("pricesmin"));
      double pricesmax = Double.parseDouble((String) params.get("pricesmax"));
      if (pricesmin < 0 || pricesmax < 0 || pricesmin > pricesmax) {
        return "ERROR: Invalid min price or max price.";
      }
    } catch (NumberFormatException nfe) {
      return "ERROR: Input should be a number.";
    }

    return "";
  }
}

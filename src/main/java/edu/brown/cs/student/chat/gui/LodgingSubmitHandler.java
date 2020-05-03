package edu.brown.cs.student.chat.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.student.api.tripadvisor.objects.Hotel;
import edu.brown.cs.student.api.tripadvisor.request.HotelRequest;
import edu.brown.cs.student.api.tripadvisor.response.HotelResponse;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class LodgingSubmitHandler implements Route {
  @Override
  public JSONObject handle(Request request, Response response) throws Exception {
    QueryParamsMap qm = request.queryMap();

    // of the form "[lat], [lon]"
    String location = qm.value("location");
    String[] locationStrings = location.split(",");
    double lat = Double.parseDouble(locationStrings[0].replaceAll("[^0-9]", ""));
    double lon = Double.parseDouble(locationStrings[1].replaceAll("[^0-9]", ""));

    // options: "Any", "Hotel", "Bed and breakfast", "Specialty"
    String type = qm.value("type");
    String subcategory = type.toLowerCase();
    if (subcategory.equals("any")) {
      subcategory = "";
    } else if (subcategory.contains("bed and breakfast")) {
      subcategory.replace("bed and breakfast", "bb");
    }

    // format: "2020-05-15" (year-month in two digits-day number in two digits)
    String checkIn = qm.value("check-in");
    // format: number
    int numNights = Integer.parseInt(qm.value("num-nights"));/////// *** CHANGED!

    // min rating; options: "Any", "1 star", "2 star", "3 star", "4 star", or "5
    // star"
    String rating = qm.value("rating");
    String hotelClass = rating.replaceAll("[^0-9,]", "");

    // format: double
    double minPrice = Double.parseDouble(qm.value("min-price"));/////// *** CHANGED!
    double maxPrice = Double.parseDouble(qm.value("max-price"));/////// *** CHANGED!

    // format: number
    int numRooms = Integer.parseInt(qm.value("num-rooms"));

    /*
     * (TODO) create a map/list/html string of lodging options with information to
     * be displayed for all search results, (TODO) and set equal to v1 in variables;
     * append all potential errors into a string and set as v2
     */

    Map<String, Object> params = new HashMap<>();
    params.put("limit", 10); // adjust
    params.put("lang", "en_US"); // adjust
    params.put("currency", "USD"); // adjust
    params.put("lunit", "mi"); // adjust
    params.put("latitude", lat);
    params.put("longitude", lon);
    params.put("checkin", checkIn);
    params.put("subcategory", type);
    params.put("nights", numNights);
    params.put("rooms", numRooms);
    params.put("hotel_class", hotelClass);
    params.put("pricesmin", minPrice);
    params.put("pricesmax", maxPrice);

    HotelResponse hr = new HotelResponse(new HotelRequest(params));
    List<Hotel> hotels = hr.getData();

    StringBuilder sb = new StringBuilder();

    for (Hotel hotel : hotels) {
      sb.append(hotel.toString() + "\n");
    }

    String msg = "";
    if (hotels.isEmpty()) {
      msg = "No matching result.";
    }

    Map<String, String> variables = ImmutableMap.of("lodging_result", sb.toString(),
        "lodging_errors", msg);
    return new JSONObject(variables);
  }
}

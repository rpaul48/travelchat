package edu.brown.cs.student.chat.gui;

import com.google.common.collect.ImmutableMap;
import org.json.JSONObject;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class LodgingSubmitHandler implements Route {
  @Override
  public JSONObject handle(Request request, Response response) throws Exception {
    QueryParamsMap qm = request.queryMap();

    //of the form "[lat], [lon]"
    String location = qm.value("location");
    //options: "Any", "Hotel", "Bed and breakfast", "Specialty"
    String type = qm.value("type");
    //format: "2020-05-15" (year-month in two digits-day number in two digits)
    String checkIn = qm.value("check-in");
    //format: "2020-05-15" (year-month in two digits-day number in two digits)
    String checkOut = qm.value("check-out");
    //min rating; options: "Any", "1 star", "2 star", "3 star", "4 star", or "5 star"
    String rating = qm.value("rating");
    //options: $, $$, or $$$
    String price = qm.value("price");
    //format: number
    int numRooms = Integer.parseInt(qm.value("num-rooms"));

    /*(TODO) create a map/list/html string of lodging options with information to be displayed for all search results,
    (TODO) and set equal to v1 in variables; append all potential errors into a string and set as v2*/

    Map<String, String> variables = ImmutableMap.of("lodging_result", "", "lodging_errors", "");
    return new JSONObject(variables);
  }
}

package edu.brown.cs.student.chat.gui;

import com.google.common.collect.ImmutableMap;
import org.json.JSONObject;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class FlightsSubmitHandler implements Route {
  @Override
  public JSONObject handle(Request request, Response response) throws Exception {
    QueryParamsMap qm = request.queryMap();

    //of the form "[lat], [lon]"
    String location = qm.value("location");
    //format: three-letter airport code
    String depart = qm.value("depart");
    //format: three-letter airport code
    String destination = qm.value("destination");
    //format: non-negative integer
    int adults = Integer.parseInt(qm.value("adults"));
    //format: non-negative integer
    int children = Integer.parseInt(qm.value("children"));
    //format: non-negative integer
    int seniors = Integer.parseInt(qm.value("seniors"));
    //options: "Any", "0", "1", "2+" (maximum number of stops)
    String maxStops = qm.value("numStops");
    //options: "Any", "Economy", "Premium Economy", "Business", "First"
    String flightClass = qm.value("flightClass");

    /*(TODO) create a map/list/html string of flights options with information to be displayed for all search results,
    (TODO) and set equal to v1 in variables; append all potential errors into a string and set as v2*/

    Map<String, String> variables = ImmutableMap.of("flights_result", "", "flights_errors", "");
    return new JSONObject(variables);
  }
}

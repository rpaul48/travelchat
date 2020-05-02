package edu.brown.cs.student.chat.gui;

import com.google.common.collect.ImmutableMap;
import org.json.JSONObject;
import spark.*;

import java.util.Map;

public class ActivitiesSubmitHandler implements Route {
  @Override
  public JSONObject handle(Request request, Response response) {
    QueryParamsMap qm = request.queryMap();

    //max miles from location; either 1, 2, 5, or 10
    int miles = Integer.parseInt(qm.value("miles"));
    //of the form "[lat], [lon]"
    String location = qm.value("location");

    /*(TODO) create a map/list/html string of activities with information to be displayed for all search results,
    (TODO) and set equal to v1 in variables; append all potential errors into a string and set as v2*/

    Map<String, String> variables = ImmutableMap.of("activities_result", "", "activities_errors", "");
    return new JSONObject(variables);
  }
}

package edu.brown.cs.student.chat.gui;

import com.google.common.collect.ImmutableMap;
import org.json.JSONObject;
import spark.*;

import java.util.Map;

public class BrowseActivitiesHandler implements Route {
  @Override
  public JSONObject handle(Request request, Response response) {
    QueryParamsMap qm = request.queryMap();

    //max miles from location; either 1, 2, 5, or 10
    int miles = Integer.parseInt(qm.value("miles"));
    //of the form "lat, lon"
    String location = qm.value("location");
    /*an string of activity categories of the form "type1,type2,type3"; (there are no spaces after commas)
    options: All, Boat Tours & Water Sports, Fun & Game, Nature & Parks, Sights & Landmarks, Shopping,
    Transportation, Museums, Outdoor Activities, Spas & Wellness, Classes & Workshops, Tours, Nightlife
     */
    String activityTypes = qm.value("activityTypes");

    /*(TODO) create a map/list/html string of activities with information to be displayed for all search results,
    (TODO) and set equal to v1 in variables; append all potential errors into a string and set as v2*/

    Map<String, String> variables = ImmutableMap.of("activities_result", "", "activities_errors", "");
    return new JSONObject(variables);
  }
}

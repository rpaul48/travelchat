package edu.brown.cs.student.chat.gui;

import com.google.common.collect.ImmutableMap;
import org.json.JSONObject;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class PlanMyDayHandler implements Route {
  @Override
  public JSONObject handle(Request request, Response response) throws Exception {

    QueryParamsMap qm = request.queryMap();

    // of the form "[lat], [lon]"
    String location = qm.value("location");

    // format: nonnegative double
    double costPerPerson = Double.parseDouble(qm.value("costPerPerson"));

    // format: time in military format: (00:00 to 23:59)
    String startTime = qm.value("startTime");

    // format: time in military format: (00:00 to 23:59)
    String endTime = qm.value("endTime");

    // max distance in miles; nonnegative integer
    int maxDist = Integer.parseInt(qm.value("maxDist"));

    // nonnegative integer
    int numMeals = Integer.parseInt(qm.value("numMeals"));

    /*
     * a string of cuisine categories of the form "type1,type2,type3"; (there are
     * no spaces after commas) options: Any, american, barbecue, chinese, italian, indian, japanese,
     * mexican, seafood, thai
     */
    String cuisineTypes = qm.value("cuisineTypes");

    /*
     * a string of activity categories of the form "type1,type2,type3"; (there are
     * no spaces after commas) options: All, Boat Tours & Water Sports, Fun & Game,
     * Nature & Parks, Sights & Landmarks, Shopping, Transportation, Museums,
     * Outdoor Activities, Spas & Wellness, Classes & Workshops, Tours, Nightlife
     */
    String activityTypes = qm.value("activityTypes");

    Map<String, String> variables = ImmutableMap.of("planMyDay_result", "",
            "planMyDay_errors", "");
    return new JSONObject(variables);
  }
}

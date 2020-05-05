package edu.brown.cs.student.chat.gui;

import java.util.Map;

import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class PlanMyDayHandler implements Route {
  @Override
  public JSONObject handle(Request request, Response response) throws Exception {

    QueryParamsMap qm = request.queryMap();

    // of the form "[lat], [lon]"
    String[] locationStrings = qm.value("location").split(",");
    String lat = locationStrings[0].replaceAll("[^0-9.]", "");
    String lon = locationStrings[1].replaceAll("[^0-9.]", "");

    // format: nonnegative double
    String costPerPerson = qm.value("costPerPerson");

    // format: time in military format: (00:00 to 23:59)
    String startTime = qm.value("startTime");

    // format: time in military format: (00:00 to 23:59)
    String endTime = qm.value("endTime");

    // max distance in miles; nonnegative integer
    String maxDist = qm.value("maxDist");

    // nonnegative integer
    String numMeals = qm.value("numMeals");

    /*
     * a string of cuisine categories of the form "type1,type2,type3"; (there are no
     * spaces after commas) options: Any, american, barbecue, chinese, italian,
     * indian, japanese, mexican, seafood, thai
     */
    String cuisineTypes = qm.value("cuisineTypes").toLowerCase();

    /*
     * a string of activity categories of the form "type1,type2,type3"; (there are
     * no spaces after commas) options: All, Boat Tours & Water Sports, Fun & Game,
     * Nature & Parks, Sights & Landmarks, Shopping, Transportation, Museums,
     * Outdoor Activities, Spas & Wellness, Classes & Workshops, Tours, Nightlife
     */
    String activityTypes = qm.value("activityTypes");

    Map<String, String> variables = ImmutableMap.of("planMyDay_result", "", "planMyDay_errors", "");
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
      if (Double.parseDouble((String) params.get("maxDist")) < 0) {
        return "ERROR: Max distance cannot be negative.";
      }
    } catch (NumberFormatException nfe) {
      return "ERROR: Max distance should be a number.";
    }

    try {
      if (Integer.parseInt((String) params.get("numMeals")) < 0) {
        return "ERROR: Number of meals cannot be negative.";
      }
    } catch (NumberFormatException nfe) {
      return "ERROR: Number of meals should be a number.";
    }

    if (!((String) params.get("startTime")).matches("\\d{2}:\\d{2}")
        || !((String) params.get("endTime")).matches("\\d{2}:\\d{2}")) {
      return "ERROR: Start time or end time is in incorrect format.";
    }
    return "";
  }
}

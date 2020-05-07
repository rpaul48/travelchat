package edu.brown.cs.student.chat.gui;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests PlanMyDayHandler Class (user input type validity).
 */
public class PlanMyDayHandlerTest {
  @Test
  public void testCorrectParams() {
    Map<String, Object> fields = new HashMap<>();
    fields.put("limit", Constants.LIMIT);
    fields.put("lang", Constants.LANG);
    fields.put("currency", Constants.CURRENCY);
    fields.put("latitude", "12.91285");
    fields.put("longitude", "100.87808");

    assertEquals(paramsAreValid(fields), "");
  }

  @Test
  public void testMissingLat() {
    Map<String, Object> fields = new HashMap<>();
    fields.put("limit", Constants.LIMIT);
    fields.put("lang", Constants.LANG);
    fields.put("currency", Constants.CURRENCY);
    fields.put("longitude", "100.87808");

    assertEquals(paramsAreValid(fields), "ERROR: Latitude or longitude is missing.");
  }

  @Test
  public void testMissingLon() {
    Map<String, Object> fields = new HashMap<>();
    fields.put("limit", Constants.LIMIT);
    fields.put("lang", Constants.LANG);
    fields.put("currency", Constants.CURRENCY);
    fields.put("latitude", "12.91285");

    assertEquals(paramsAreValid(fields), "ERROR: Latitude or longitude is missing.");
  }

  @Test
  public void testLatLonNotNumber() {
    Map<String, Object> fields = new HashMap<>();
    fields.put("limit", Constants.LIMIT);
    fields.put("lang", Constants.LANG);
    fields.put("currency", Constants.CURRENCY);
    fields.put("latitude", "a");
    fields.put("longitude", "hello");

    assertEquals(paramsAreValid(fields), "ERROR: Latitude or longitude is not a number.");
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

    if (!(latitude >= Constants.MIN_LATITUDE && latitude <= Constants.MAX_LATITUDE
        && longitude >= Constants.MIN_LONGITUDE && longitude <= Constants.MAX_LONGITUDE)) {
      return "ERROR: Latitude or longitude is out of range.";
    }

    /*
     * Other variables for Attraction or Restaurant querying do not need to be
     * checked as they're options from dropdown menus that we know are all valid.
     */

    return "";
  }
}

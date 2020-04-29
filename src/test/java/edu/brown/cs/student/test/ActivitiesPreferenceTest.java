package edu.brown.cs.student.test;

import org.junit.Test;

public class ActivitiesPreferenceTest {

  @Test
  public void test() {
    // Outputs perfectly match.

    // HttpResponse<String> response = Unirest.get(
    // "https://tripadvisor1.p.rapidapi.com/attractions/list-by-latlng?lunit=km&currency=USD&limit=10&distance=5&lang=en_US&longitude=100.87808&latitude=12.91285")
    // .header("x-rapidapi-host", "tripadvisor1.p.rapidapi.com")
    // .header("x-rapidapi-key",
    // "9ab9c1d3bdmsha453182e940dd58p105f14jsna2fade8f7b4d").asString();
    // System.out.println(response.getBody());

    // ActivitiesPreference ap = new ActivitiesPreference();
    // Map<String, Object> fields = new HashMap<>();
    // fields.put("limit", 10); // adjust
    // fields.put("lang", "en_US"); // adjust
    // fields.put("currency", "USD"); // adjust
    // fields.put("lunit", "km"); // length unit; adjust
    // fields.put("latitude", 12.91285);
    // fields.put("longitude", 100.87808);
    // fields.put("distance", 5);
    // ap.setFields(fields);
    // System.out.println(ap.getQueryString());

    // List<Item> attractionsList = ap.parseResult();
    // System.out.println(attractionsList.size());
    // for (Item attraction : attractionsList) {
    // System.out.println(attraction.getName());
    // }
  }
}

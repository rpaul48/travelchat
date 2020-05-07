package edu.brown.cs.student.api.tripadvisor.request;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Tests HotelRequest class.
 */
public class HotelRequestTest {

  @Test
  public void test() throws UnirestException {
    HotelRequest req = new HotelRequest(null);
    assertEquals(req.getParams(), null);

    Map<String, Object> params = new HashMap<>();
    params.put("limit", 50); // adjust
    params.put("lang", "en_US"); // adjust
    params.put("currency", "USD"); // adjust
    params.put("lunit", "mi"); // adjust
    params.put("latitude", 12.91285);
    params.put("longitude", 100.87808);
    params.put("checkin", "2020-05-08");
    params.put("subcategory", "bb");
    params.put("nights", 2);
    params.put("rooms", 2);
    params.put("hotel_class", 3);

    req.setParams(params);
    assertEquals(req.getParams(), params);
  }
}

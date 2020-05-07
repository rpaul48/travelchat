package edu.brown.cs.student.api.tripadvisor.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Hotel;
import edu.brown.cs.student.api.tripadvisor.request.HotelRequest;

/**
 * Tests HotelResponse Class.
 */
public class HotelResponseTest {
  @Test
  public void testGetSetHotelRequest() throws UnirestException {
    HotelResponse resp = new HotelResponse(null);
    assertEquals(resp.getHotelRequest(), null);

    Map<String, Object> params = new HashMap<>();
    params.put("limit", 30); // adjust
    params.put("lang", "en_US"); // adjust
    params.put("currency", "USD"); // adjust
    params.put("lunit", "mi"); // adjust
    params.put("latitude", 12.91285);
    params.put("longitude", 100.87808);
    params.put("checkin", "2020-06-01");
    params.put("subcategory", "bb");
    params.put("nights", 2);
    params.put("rooms", 2);
    params.put("hotel_class", 3);
    HotelRequest req = new HotelRequest(params);
    resp.setHotelRequest(req);
    assertEquals(resp.getHotelRequest(), req);
  }

  @Test
  public void testGetData() throws UnirestException {
    Map<String, Object> params = new HashMap<>();
    params.put("limit", 30); // adjust
    params.put("lang", "en_US"); // adjust
    params.put("currency", "USD"); // adjust
    params.put("lunit", "mi"); // adjust
    params.put("latitude", 12.91285);
    params.put("longitude", 100.87808);
    params.put("checkin", "2020-06-01");
    params.put("subcategory", "bb");
    params.put("nights", 2);
    params.put("rooms", 2);
    params.put("hotel_class", 3);
    HotelResponse hr = new HotelResponse(new HotelRequest(params));
    List<Hotel> hotels = hr.getData();
    assertTrue(!(hotels == null || hotels.isEmpty()));
  }
}

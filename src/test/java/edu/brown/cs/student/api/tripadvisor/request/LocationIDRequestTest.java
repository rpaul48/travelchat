package edu.brown.cs.student.api.tripadvisor.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests LocationIDRequest class.
 */
public class LocationIDRequestTest {

  @Test
  public void testGetSetCityName() {
    LocationIDRequest req = new LocationIDRequest("Providence");
    assertEquals(req.getCityName(), "Providence");
    req.setCityName("New York");
    assertEquals(req.getCityName(), "New York");
  }

}

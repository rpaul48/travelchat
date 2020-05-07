package edu.brown.cs.student.api.tripadvisor.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class GeoLocationTest {

  @Test
  public void testGetFields() {
    Map<String, String> fields = new HashMap<String, String>();

    GeoLocation gl = new GeoLocation(fields);
    assertEquals(gl.getFields().size(), 0);

    fields.put("state", "NY");
    GeoLocation gl2 = new GeoLocation(fields);
    assertEquals(gl2.getFields().size(), 1);
    assertEquals(gl2.getFields().get("state"), "NY");
  }

}

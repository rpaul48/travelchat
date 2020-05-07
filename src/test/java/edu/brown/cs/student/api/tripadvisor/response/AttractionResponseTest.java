package edu.brown.cs.student.api.tripadvisor.response;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.request.AttractionRequest;

/**
 * Tests AttractionResponse Class.
 */
public class AttractionResponseTest {
  @Test
  public void testGetSetAttractionRequest() throws UnirestException {
    AttractionResponse resp = new AttractionResponse(null);
    assertEquals(resp.getAttractionRequest(), null);

    Map<String, Object> params = new HashMap<>();
    params.put("limit", 30); // adjust
    params.put("lang", "en_US"); // adjust
    params.put("currency", "USD"); // adjust
    params.put("lunit", "mi"); // adjust
    params.put("latitude", 12.91285);
    params.put("longitude", 100.87808);
    AttractionRequest req = new AttractionRequest(params);
    resp.setAttractionRequest(req);
    assertEquals(resp.getAttractionRequest(), req);
  }
}

package edu.brown.cs.student.api.tripadvisor.preference;

import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Item;

//public class Preference {
//  public Preference(Map<String, String> preferencesFromFrontEnd) {
//
//  }
//}

public interface Preference {
  public void buildFields();

  public String getQueryString() throws UnirestException;

  public List<Item> parseResult() throws JSONException, UnirestException;

  public double getLatitude();

  public double getLongitude();

  public Map<String, Object> getFields();

  public void setLatitude(double latitude);

  public void setLongitude(double longitude);

  public void setFields(Map<String, Object> fields);
}

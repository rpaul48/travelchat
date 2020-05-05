package edu.brown.cs.student.api.tripadvisor.querier;

/**
 * An API querier.
 *
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class Querier {
  private String apiKey;
  // i.e. RapidAPI
  private String apiHost;

  public Querier(String apiKey, String apiHost) {
    this.apiKey = apiKey;
    this.apiHost = apiHost;
  }

  public String getAPIHost() {
    return apiHost;
  }

  public void setAPIHost(String host) {
    this.apiHost = host;
  }

  public String getAPIKey() {
    return apiKey;
  }

  public void setAPIKey(String key) {
    this.apiKey = key;
  }
}

package edu.brown.cs.student.api.tripadvisor.querier;

/**
 * An API querier.
 *
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class Querier {
    private String APIKey;
    // i.e. RapidAPI
    private String APIHost;

    public Querier(String APIKey, String APIHost) {
        this.APIKey = APIKey;
        this.APIHost = APIHost;
    }

    public String getAPIHost() {
        return APIHost;
    }

    public void setAPIHost(String APIHost) {
        this.APIHost = APIHost;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }
}

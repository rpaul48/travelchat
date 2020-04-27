package api.tripadvisor.request;

import java.util.HashMap;
import java.util.Map;

/**
 * An API request. Made to handle front-end-passed maps of parameters.
 *
 * Note: This assumes "givenParams" is formatted correctly for the specific request.
 *
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class Request {
    private String hostURL;
    private String currency;
    private Map<String, Object> params;

    public Request(String hostURL, String currency, Map<String, Object> givenParams) {
        this.hostURL = hostURL;
        this.currency = currency;
        this.params = new HashMap<>();
        for (Map.Entry<String, Object> entry : givenParams.entrySet()) {
            // Each map should have keys for every possible parameter but unused parameters have null or empty values.
            if (entry.getValue() != null && entry.getValue() != "") {
                params.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public String getHostURL() {
        return hostURL;
    }

    public void setHostURL(String hostURL) {
        this.hostURL = hostURL;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

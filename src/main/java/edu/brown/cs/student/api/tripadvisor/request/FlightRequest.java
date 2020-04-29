package edu.brown.cs.student.api.tripadvisor.request;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A request for flights from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class FlightRequest extends Request{
    public FlightRequest(String hostURL, String currency, Map<String, Object> givenParams) {
        super(hostURL, currency, givenParams);
    }

    public static String getSearchHash(JSONObject allFields) {
        JSONObject summary = allFields.getJSONObject("summary");
        return summary.getString("sh");
    }

    public static String getFlightID(JSONObject allFields) {
        JSONArray itineraries = allFields.getJSONArray("itineraries");
        JSONObject flight = itineraries.getJSONObject(0);
        JSONArray idArr = flight.getJSONArray("l");
        return idArr.getJSONObject(0).getString("id");
    }

    public static String getOrigin(JSONObject allFields) {
        JSONObject search_params = allFields.getJSONObject("search_params");
        String origin = search_params.getJSONArray("s").getJSONObject(0).getString("o");
        return origin;
    }

    public static String getDest(JSONObject allFields) {
        JSONObject search_params = allFields.getJSONObject("search_params");
        String dest = search_params.getJSONArray("s").getJSONObject(0).getString("d");
        return dest;
    }

    public static String getPrice(JSONObject allFields) {
        JSONArray itineraries = allFields.getJSONArray("itineraries");
        JSONObject flight = itineraries.getJSONObject(0);
        JSONArray idArr = flight.getJSONArray("l");
        return idArr.getJSONObject(0).getJSONObject("pr").getString("dp");
    }

    public static String getCabinClass(JSONObject allFields) {
        JSONArray itineraries = allFields.getJSONArray("itineraries");
        JSONObject flight = itineraries.getJSONObject(0);
        JSONArray idArr = flight.getJSONArray("l");
        return idArr.getJSONObject(0).getJSONObject("fb").getString("nm");
    }

    public static String getCarrier(JSONObject allFields) {
        JSONArray itineraries = allFields.getJSONArray("itineraries");
        JSONObject flight = itineraries.getJSONObject(0);
        JSONArray idArr = flight.getJSONArray("l");
        return idArr.getJSONObject(0).getString("s");
    }

    public static String getDuration(JSONObject allFields) {
        JSONArray itineraries = allFields.getJSONArray("itineraries");
        JSONObject flight = itineraries.getJSONObject(0);
        JSONArray arr = flight.getJSONArray("f");
        String departDate = arr.getJSONObject(0).getJSONArray("l").getJSONObject(0).getString("dd");
        String arriveDate = arr.getJSONObject(0).getJSONArray("l").getJSONObject(0).getString("ad");
        return departDate + " -> " + arriveDate;
    }

    public static String getLayover(JSONObject allFields) {
        JSONArray itineraries = allFields.getJSONArray("itineraries");
        JSONObject flight = itineraries.getJSONObject(0);
        JSONArray arr = flight.getJSONArray("f");
        String layover = "";
        try {
            layover = arr.getJSONObject(0).getJSONArray("lo").getJSONObject(0).getString("t");
            layover = layover.substring(layover.indexOf('>') + 1, layover.lastIndexOf('<'));
            String locationOfLayover = arr.getJSONObject(0).getJSONArray("lo").getJSONObject(0)
                    .getString("s");
            layover += " in " + locationOfLayover;
        } catch (Exception e) {
            System.out.println("No layover.");
        }
        return layover;
    }

    public static String getBookingURL(String sh, String d, String o, String id, String sid) {
        Map<String, String> params = new HashMap<>();
        params.put("searchHash", sh);
        params.put("Dest", d);
        params.put("id", id);
        params.put("Orig", o);
        params.put("searchId", sid);

        ImmutableMap<String, Object> immutableParams = ImmutableMap.copyOf(params);

        String hostURL = "https://tripadvisor1.p.rapidapi.com/flights/get-booking-url";
        // Request headers (with free account's key)
        String x_rapidapi_host = "tripadvisor1.p.rapidapi.com";
        String x_rapidapi_key = "aaf4f074c6msh0940f8b6e880750p1f240bjsne42d7f349197";
        // Send a request and handle response
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(hostURL)
                    .queryString(immutableParams)
                    .header("x-rapidapi-host", x_rapidapi_host)
                    .header("x-rapidapi-key", x_rapidapi_key)
                    .asJson();
        } catch (Exception e) {
            return "Could not get booking URL.";
        }
        JSONObject obj = new JSONObject(response);
        JSONObject body = obj.getJSONObject("body");
        String s = "";
        try {
            s = body.getJSONArray("array").getJSONObject(0).getString("partner_url");
        } catch (Exception e) {
            s = "Could not get booking URL.";
        }
        return s;
    }


    public static void printHTTPResponse(HttpResponse<JsonNode> response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
    }

    public static HttpResponse<JsonNode> pollFlights(String sid) throws UnirestException {
        Map<String, String> params = new HashMap<>();
        params.put("sid", sid);
        params.put("currency", "USD");
        params.put("so", "Sorted by Best Value");

        ImmutableMap<String, Object> immutableParams = ImmutableMap.copyOf(params);

        String hostURL = "https://tripadvisor1.p.rapidapi.com/flights/poll";
        // Request headers (with free account's key)
        String x_rapidapi_host = "tripadvisor1.p.rapidapi.com";
        String x_rapidapi_key = "aaf4f074c6msh0940f8b6e880750p1f240bjsne42d7f349197";
        // Send a request and handle response
        HttpResponse <JsonNode> response = Unirest.get(hostURL)
                .queryString(immutableParams)
                .header("x-rapidapi-host", x_rapidapi_host)
                .header("x-rapidapi-key", x_rapidapi_key)
                .asJson();
        return response;
    }

    public static HttpResponse<JsonNode> createSession() throws UnirestException {
        Map<String, String> params = new HashMap<>();
        params.put("d1", "JFK");
        params.put("o1", "LAX");
        params.put("dd1", "2020-05-08");
        params.put("currency", "USD");
        params.put("ta", "1");
        params.put("c", "0");

        ImmutableMap<String, Object> immutableParams = ImmutableMap.copyOf(params);

        String hostURL = "https://tripadvisor1.p.rapidapi.com/flights/create-session";
        // Request headers (with free account's key)
        String x_rapidapi_host = "tripadvisor1.p.rapidapi.com";
        String x_rapidapi_key = "aaf4f074c6msh0940f8b6e880750p1f240bjsne42d7f349197";
        // Send a request and handle response
        HttpResponse <JsonNode> response = Unirest.get(hostURL)
                .queryString(immutableParams)
                .header("x-rapidapi-host", x_rapidapi_host)
                .header("x-rapidapi-key", x_rapidapi_key)
                .asJson();
        return response;
    }

    public static String getSID(HttpResponse<JsonNode> response) {
        JSONObject obj = new JSONObject(response);
        JSONObject body = obj.getJSONObject("body");
        JSONArray array = body.getJSONArray("array");
        // All fields are in a JSON at index 0, for some reason...
        JSONObject allFields = array.getJSONObject(0);
        // NOW... we can index into the fields
        JSONObject searchParamsJSON = allFields.getJSONObject("search_params");
        String sid = searchParamsJSON.getString("sid");
        return sid;
    }

}

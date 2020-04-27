package edu.brown.cs.student.api.tripadvisor.util;

import java.util.Map;

/**
 * A location on Earth, described by fields:
 * "street1"
 * "street2"
 * "city"
 * "state"
 * "country"
 * "postalcode"
 *
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class GeoLocation {
    public Map<String, String> fields;

    public GeoLocation(Map<String, String> fields) {
        this.fields = fields;
    }

    public Map<String, String> getFields() {
        return fields;
    }
}

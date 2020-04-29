package edu.brown.cs.student.api.tripadvisor.objects;

/**
 * A flight, as defined by the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class Flight {
    private String bookingURL;
    private String price;
    private String cabinClass;
    private String carrier;
    private String layover;
    // Score is defined by TripAdvisor API.
    //private String flightScore;
    private String searchHash;
    private String searchID;
    private String dest;
    private String origin;
    private String id;

    public Flight(String bookingURL, String price, String cabinClass, String carrier, String layover, String searchHash,
                  String searchID, String dest, String origin, String id) {
        this.bookingURL = bookingURL;
        this.price = price;
        this.cabinClass = cabinClass;
        this.carrier = carrier;
        this.layover = layover;
        this.searchHash = searchHash;
        this.searchID = searchID;
        this.dest = dest;
        this.origin = origin;
        this.id = id;
    }

    public String getBookingURL() {
        return bookingURL;
    }

    public void setBookingURL(String bookingURL) {
        this.bookingURL = bookingURL;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getLayover() {
        return layover;
    }

    public void setLayover(String layover) {
        this.layover = layover;
    }

    public String getSearchHash() {
        return searchHash;
    }

    public void setSearchHash(String searchHash) {
        this.searchHash = searchHash;
    }

    public String getSearchID() {
        return searchID;
    }

    public void setSearchID(String searchID) {
        this.searchID = searchID;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

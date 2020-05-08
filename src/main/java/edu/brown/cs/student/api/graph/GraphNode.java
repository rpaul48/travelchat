package edu.brown.cs.student.api.graph;

import java.util.List;

import edu.brown.cs.student.api.tripadvisor.objects.Item;

/**
 * A class representing a GraphNode object.
 */
public class GraphNode implements IVertex<GraphNode, WayEdge> {

  /**
   * These are fields for this class.
   *
   * id - a String, which represents the id of this node from the database
   *
   * latitude - a double representing the latitude of the node
   *
   * longitude - a double representing the longitude of the node
   *
   * edgesList - a list of the way edges that start from this node
   *
   * proxy - a MapsProxy, which is used to load in data from the SQL file
   *
   * radius - a double representing the radius of the Earth
   */
  private Item item;
  private double latitude;
  private double longitude;
  private List<WayEdge> edgesList;
  private final double radius = 6371.0;

  /**
   * This is the constructor for this class.
   *
   * @param item     - data of the node of type Item
   * @param lat      - a double representing the latitude of the node
   * @param lon      - a double representing the longitude of the node
   * @param edgesList - a list of the node's edges
   */
  public GraphNode(Item item, double lat, double lon, List<WayEdge> edgesList) {
    this.item = item;
    this.latitude = lat;
    this.longitude = lon;
    this.edgesList = edgesList;
  }

  /**
   * This method returns the latitude of the node.
   *
   * @return a double representing the latitude of the node
   */
  public double getLat() {
    return latitude;
  }

  /**
   * This method returns the longitude of the node.
   *
   * @return a double representing the longitude of the node
   */
  public double getLon() {
    return longitude;
  }

  public void setEdges(List<WayEdge> edges) {
    this.edgesList = edges;
  }

  @Override
  public List<WayEdge> getEdges() {
    return edgesList;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GraphNode)) {
      return false;
    }

    // compare using IDs because IDs are unique
    GraphNode otherGraphNode = (GraphNode) obj;
    return otherGraphNode.getItem().equals(this.item);
  }

  @Override
  public int hashCode() {
    return item.hashCode();
  }

  public Item getItem() {
    return item;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public List<WayEdge> getEdgesList() {
    return edgesList;
  }

  public double getRadius() {
    return radius;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public void setEdgesList(List<WayEdge> edgesList) {
    this.edgesList = edgesList;
  }

  @Override
  public double getHeuristic(GraphNode node1, GraphNode node2) {
    double lat1 = node1.getLat();
    double lon1 = node1.getLon();
    double lat2 = node2.getLat();
    double lon2 = node2.getLon();

    double totalLat = Math.toRadians(lat2 - lat1);
    double totalLon = Math.toRadians(lon2 - lon1);
    double radLat1 = Math.toRadians(lat1);
    double radLat2 = Math.toRadians(lat2);

    double transitory = Math.pow(Math.sin(totalLat / 2.0), 2.0)
        + (Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(totalLon / 2.0), 2.0));
    double distance = (2.0 * radius * Math.asin(Math.sqrt(transitory)));
    return distance;
  }

}

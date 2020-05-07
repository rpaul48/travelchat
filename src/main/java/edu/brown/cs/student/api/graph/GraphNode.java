//package edu.brown.cs.student.api.graph;
//
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//
//import com.google.common.cache.LoadingCache;
//
//import edu.brown.cs.student.graph.IVertex;
//
///**
// * A class representing a GraphNode object.
// */
//public class GraphNode implements IVertex<GraphNode, WayEdge> {
//
//  /**
//   * These are fields for this class.
//   *
//   * id - a String, which represents the id of this node from the database
//   *
//   * latitude - a double representing the latitude of the node
//   *
//   * longitude - a double representing the longitude of the node
//   *
//   * edgesList - a list of the way edges that start from this node
//   *
//   * proxy - a MapsProxy, which is used to load in data from the SQL file
//   *
//   * radius - a double representing the radius of the Earth
//   */
//  private String id;
//  private double latitude;
//  private double longitude;
//  private List<WayEdge> edgesList;
//  private MapsProxy proxy;
//  private final double radius = 6371.0;
//
//  /**
//   * This is the constructor for this class.
//   *
//   * @param id       - a String representing the ID of the actor
//   * @param lat      - a double representing the latitude of the node
//   * @param lon      - a double representing the longitude of the node
//   * @param waysList - a List of WayEdges, where the ways are outgoing edges
//   *                 from this node
//   * @param proxy    - a MapsProxy, which is used to load in data from the SQL
//   *                 file
//   */
//  public GraphNode(String id, double lat, double lon, List<WayEdge> waysList,
//      MapsProxy proxy) {
//    this.id = id;
//    this.latitude = lat;
//    this.longitude = lon;
//    this.edgesList = waysList;
//    this.proxy = proxy;
//  }
//
//  /**
//   * This method returns the ID of the node.
//   *
//   * @return a String representing the ID of the node
//   */
//  public String getNodeID() {
//    return id;
//  }
//
//  /**
//   * This method returns the latitude of the node.
//   *
//   * @return a double representing the latitude of the node
//   */
//  public double getLat() {
//    return latitude;
//  }
//
//  /**
//   * This method returns the longitude of the node.
//   *
//   * @return a double representing the longitude of the node
//   */
//  public double getLon() {
//    return longitude;
//  }
//
//  @Override
//  public List<WayEdge> getEdges() {
//    if (!edgesList.isEmpty()) {
//      return edgesList;
//    }
//
//    try {
//      LoadingCache<GraphNode, List<WayEdge>> nodeWayEdgeCache = proxy
//          .getNodeWayEdgeCache();
//
//      if (nodeWayEdgeCache == null) {
//        System.err.println(
//            "ERROR: the database must be set before the cache can be used");
//      }
//
//      // get the outgoing edges for this Actor
//      List<WayEdge> newWayEdges = nodeWayEdgeCache.get(this);
//
//      if (newWayEdges == null) {
//        // an SQLException occurred
//        System.err.println("ERROR: problem accessing the SQL file");
//        return null;
//      }
//
//      for (WayEdge wayEdge : newWayEdges) {
//        edgesList.add(wayEdge);
//      }
//
//      return edgesList;
//
//    } catch (ExecutionException execErr) {
//      System.err.println("ERROR: An error occurred accessing the cache");
//      return null;
//    }
//  }
//
//  @Override
//  public boolean equals(Object obj) {
//    if (!(obj instanceof GraphNode)) {
//      return false;
//    }
//
//    // compare using IDs because IDs are unique
//    GraphNode otherGraphNode = (GraphNode) obj;
//    return otherGraphNode.getNodeID().equals(this.id);
//  }
//
//  @Override
//  public int hashCode() {
//    return id.hashCode();
//  }
//
//  @Override
//  public double getHeuristic(GraphNode node1, GraphNode node2) {
//    double lat1 = node1.getLat();
//    double lon1 = node1.getLon();
//    double lat2 = node2.getLat();
//    double lon2 = node2.getLon();
//
//    double totalLat = Math.toRadians(lat2 - lat1);
//    double totalLon = Math.toRadians(lon2 - lon1);
//    double radLat1 = Math.toRadians(lat1);
//    double radLat2 = Math.toRadians(lat2);
//
//    double transitory = Math.pow(Math.sin(totalLat / 2.0), 2.0)
//        + (Math.cos(radLat1) * Math.cos(radLat2)
//            * Math.pow(Math.sin(totalLon / 2.0), 2.0));
//    double distance = (2.0 * radius * Math.asin(Math.sqrt(transitory)));
//    return distance;
//  }
//
//}

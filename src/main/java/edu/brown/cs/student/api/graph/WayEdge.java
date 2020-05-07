//package edu.brown.cs.student.api.graph;
//
///**
// * A class representing a WayEdge object, which implements the IEdge interface.
// */
//public class WayEdge implements IEdge<GraphNode, WayEdge> {
//
//  /**
//   * These are fields for this class.
//   *
//   * way - a Way, which represents the way associated with this WayEdge
//   *
//   * startNode - a GraphNode, which represents the start node of this edge
//   *
//   * endNode - a GraphNode, which represents the node at which this Way edge
//   * terminates
//   *
//   * radius - a double representing the radius of the Earth
//   */
//  private Way way;
//  private GraphNode startNode;
//  private GraphNode endNode;
//  private final double radius = 6371.0;
//
//  /**
//   * This is the constructor for the WayEdge class.
//   *
//   * @param way       - a Way, which represents the way associated with this
//   *                  WayEdge
//   * @param startNode - a GraphNode, which represents the start node of this edge
//   * @param endNode   - The GraphNode representing the node the Way edge
//   *                  terminates at
//   */
//  public WayEdge(Way way, GraphNode startNode, GraphNode endNode) {
//    this.way = way;
//    this.startNode = startNode;
//    this.endNode = endNode;
//  }
//
//  /**
//   * This method calculates the Haversine distance between two nodes.
//   *
//   * @param node1 GraphNode
//   * @param node2 GraphNode
//   * @return Haversine distance between two nodes
//   */
//  public double calculateDistance(GraphNode node1, GraphNode node2) {
//    double lat1 = node1.getLat();
//    double lon1 = node1.getLon();
//    double lat2 = node2.getLat();
//    double lon2 = node2.getLon();
//    double radLat1 = Math.toRadians(lat1);
//    double radLat2 = Math.toRadians(lat2);
//    double totalLat = Math.toRadians(lat2 - lat1);
//    double totalLon = Math.toRadians(lon2 - lon1);
//
//    double transitory = Math.pow(Math.sin(totalLat / 2.0), 2.0)
//        + (Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(totalLon / 2.0), 2.0));
//    double distance = (2.0 * radius * Math.asin(Math.sqrt(transitory)));
//    return distance;
//  }
//
//  @Override
//  public double getWeight() {
//    double havDist = calculateDistance(startNode, endNode);
//    return havDist;
//  }
//
//  @Override
//  public GraphNode getStartVertex() {
//    return startNode;
//  }
//
//  @Override
//  public GraphNode getEndVertex() {
//    return endNode;
//  }
//
//  /**
//   * This method returns the ID of the way associated with this way edge.
//   *
//   * @return a String representing the ID of the way associated with the way edge
//   */
//  public String getWayID() {
//    return way.getID();
//  }
//
//}

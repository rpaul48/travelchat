package edu.brown.cs.student.api.graph;

/**
 * A class representing a WayEdge object, which implements the IEdge interface.
 */
public class WayEdge implements IEdge<GraphNode, WayEdge> {

  /**
   * These are fields for this class.
   *
   * way - a Way, which represents the way associated with this WayEdge
   *
   * startNode - a GraphNode, which represents the start node of this edge
   *
   * endNode - a GraphNode, which represents the node at which this Way edge
   * terminates
   *
   * radius - a double representing the radius of the Earth
   */
  private GraphNode startNode;
  private GraphNode endNode;
  private final double EARTH_RADIUS = 6371.0;

  /**
   * This is the constructor for the WayEdge class.
   *
   * @param way       - a Way, which represents the way associated with this
   *                  WayEdge
   * @param startNode - a GraphNode, which represents the start node of this edge
   * @param endNode   - The GraphNode representing the node the Way edge
   *                  terminates at
   */
  public WayEdge(GraphNode startNode, GraphNode endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }

  /**
   * This method calculates the Haversine distance between two nodes.
   *
   * @param node1 GraphNode
   * @param node2 GraphNode
   * @return Haversine distance between two nodes
   */
  public double calculateDistance(GraphNode node1, GraphNode node2) {
    double lat1 = node1.getLat();
    double lon1 = node1.getLon();
    double lat2 = node2.getLat();
    double lon2 = node2.getLon();
    double radLat1 = Math.toRadians(lat1);
    double radLat2 = Math.toRadians(lat2);
    double totalLat = Math.toRadians(lat2 - lat1);
    double totalLon = Math.toRadians(lon2 - lon1);

    double transitory = Math.pow(Math.sin(totalLat / 2.0), 2.0)
        + (Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(totalLon / 2.0), 2.0));
    double distance = (2.0 * EARTH_RADIUS * Math.asin(Math.sqrt(transitory)));
    return distance;
  }

  @Override
  public double getWeight() {
    double havDist = calculateDistance(startNode, endNode);
    return havDist;
  }

  @Override
  public GraphNode getStartVertex() {
    return startNode;
  }

  @Override
  public GraphNode getEndVertex() {
    return endNode;
  }

  public GraphNode getStartNode() {
    return startNode;
  }

  public GraphNode getEndNode() {
    return endNode;
  }

  public void setStartNode(GraphNode startNode) {
    this.startNode = startNode;
  }

  public void setEndNode(GraphNode endNode) {
    this.endNode = endNode;
  }
}

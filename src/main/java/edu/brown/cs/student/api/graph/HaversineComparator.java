package edu.brown.cs.student.api.graph;

import java.util.Comparator;

import edu.brown.cs.student.chat.gui.Constants;

/**
 * A class to compare nodes with respect to Haversine distances.
 *
 * @param <T> GraphNode
 */
public class HaversineComparator<T extends GraphNode> implements Comparator<T> {

  /**
   * These are the fields for this class.
   *
   * dim - the dimension of the kd-tree
   *
   * target - an object of type T that represents a target to which the haversine
   * distance needs to be calculated
   *
   * radius - a double representing the radius of the Earth
   */
  private int dim;
  private T target;

  /**
   * The constructor for the haversine comparator class.
   *
   * @param dim    dimension of the coordinates
   * @param target target node being compared
   */
  public HaversineComparator(int dim, T target) {
    this.setDim(dim);
    this.target = target;
  }

  /**
   * Getter for the dimension field.
   *
   * @return an integer representing the dimension
   */
  public int getDim() {
    return dim;
  }

  /**
   * Setter for the dimension field.
   *
   * @param dim - an int representing the dimension
   */
  public void setDim(int dim) {
    this.dim = dim;
  }

  /**
   * This method calculates the Haversine distance between two points.
   *
   * @param node1 KDNode
   * @param node2 KDNode
   * @return Haversine distance between two nodes
   */
  public double calculateDistance(T node1, T node2) {
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
    double distance = (2.0 * Constants.EARTH_RADIUS * Math.asin(Math.sqrt(transitory)));
    return distance;
  }

  /**
   * This method compares two nodes using Haversine distance.
   *
   * @param node1 KDNode
   * @param node2 KDNode
   * @return integer to represent relationship
   */
  @Override
  public int compare(T node1, T node2) {
    Double d1 = calculateDistance(node1, this.target);
    Double d2 = calculateDistance(node2, this.target);

    return Double.compare(d1, d2);
  }
}

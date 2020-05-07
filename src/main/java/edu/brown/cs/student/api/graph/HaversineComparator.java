//package edu.brown.cs.student.api.graph;
//
//import java.util.Comparator;
//import java.util.List;
//
//import edu.brown.cs.student.stars.KDNode;
//import edu.brown.cs.student.stars.NodeInterface;
//
///**
// * A class to compare nodes with respect to Haversine distances.
// *
// * @param <T> KDNode
// */
//public class HaversineComparator<T extends KDNode<NodeInterface>>
//    implements Comparator<T> {
//
//  /**
//   * These are the fields for this class.
//   *
//   * dim - the dimension of the kd-tree
//   *
//   * target - an object of type T that represents a target to which the
//   * haversine distance needs to be calculated
//   *
//   * radius - a double representing the radius of the Earth
//   */
//  private int dim;
//  private T target;
//  private final double radius = 6371.0;
//
//  /**
//   * The constructor for the haversine comparator class.
//   *
//   * @param dim    dimension of the coordinates
//   * @param target target node being compared
//   */
//  public HaversineComparator(int dim, T target) {
//    this.setDim(dim);
//    this.target = target;
//  }
//
//  /**
//   * Getter for the dimension field.
//   *
//   * @return an integer representing the dimension
//   */
//  public int getDim() {
//    return dim;
//  }
//
//  /**
//   * Setter for the dimension field.
//   *
//   * @param dim - an int representing the dimension
//   */
//  public void setDim(int dim) {
//    this.dim = dim;
//  }
//
//  /**
//   * This method calculates the Haversine distance between two points.
//   *
//   * @param node1 KDNode
//   * @param node2 KDNode
//   * @return Haversine distance between two nodes
//   */
//  public double calculateDistance(T node1, T node2) {
//    List<Double> coord1 = node1.getField().getCoord();
//    List<Double> coord2 = node2.getField().getCoord();
//
//    double lat1 = coord1.get(0);
//    double lon1 = coord1.get(1);
//    double lat2 = coord2.get(0);
//    double lon2 = coord2.get(1);
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
//  /**
//   * This method compares two nodes using Haversine distance.
//   *
//   * @param node1 KDNode
//   * @param node2 KDNode
//   * @return integer to represent relationship
//   */
//  @Override
//  public int compare(T node1, T node2) {
//    Double d1 = calculateDistance(node1, this.target);
//    Double d2 = calculateDistance(node2, this.target);
//
//    return Double.compare(d1, d2);
//  }
//}

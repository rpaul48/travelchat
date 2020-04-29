package edu.brown.cs.crusch.kdtree;

import java.util.Comparator;

/**
 * Comparator for sorting a Collection of KDTreeNodes by a certain point dimension.
 */
public class AxisSorter implements Comparator<KDTreeNode> {
  private int axis;

  /**
   * Constructs an axis sorter that sorts by the given axis.
   * @param currentAxis integer representing an axis by the index of an array.
   */
  public AxisSorter(int currentAxis) {
    this.axis = currentAxis;
  }

  /**
   * Compares two KDTreeNodes by a certain index of their point arrays.
   * @param o1 first KDTreeNode to be compared
   * @param o2 second KDTreeNode to be compared
   * @return integer -1 for o1 less than o2, 0 for o1 equals o2, 1 for o1 greater than o2
   */
  @Override
  public int compare(KDTreeNode o1, KDTreeNode o2) {
    double[] o1Vals = o1.getPoint();
    double[] o2Vals = o2.getPoint();
    return Double.compare(o1Vals[this.axis], o2Vals[this.axis]);
  }
}

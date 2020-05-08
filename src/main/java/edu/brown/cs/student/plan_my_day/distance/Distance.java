package edu.brown.cs.student.plan_my_day.distance;

/**
 * Interface that all distance measures implement.
 * @author kallifeinberg
 * @version 1.0
 */
public interface Distance {

  /**
   * Computes the distance between two objects.
   *
   * @param coords1 The coordinates of the first object.
   * @param coords2 the coordinates of the second object.
   * @return The distance between the two objects.
   */
  double getDistance(double[] coords1, double[] coords2);

}

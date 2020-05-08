package edu.brown.cs.student.plan_my_day.distance;

/**
 * Measures Euclidean distance between coordinates.
 * @author kallifeinberg
 * @version 1.0
 */
public class Euclidean implements Distance {

  /**
   * Computes the Euclidean distance between two objects.
   *
   * @param coords1 The coordinates of the first object.
   * @param coords2 the coordinates of the second object.
   * @return The Euclidean distance between the two objects.
   */
  @Override
  public double getDistance(double[] coords1, double[] coords2) {
    if (coords1.length != coords2.length) {
      System.out.println("ERROR: Invalid coordinate field.");
      return 0;
    }
    double distance = 0;
    for (int i = 0; i < coords1.length; i++) {
      distance += Math.pow((coords1[i] - coords2[i]), 2);
    }
    return Math.sqrt(distance);

  }
}


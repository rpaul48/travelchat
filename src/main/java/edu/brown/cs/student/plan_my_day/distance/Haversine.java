package edu.brown.cs.student.plan_my_day.distance;

/**
 * Measures Haversine distance between coordinates.
 *
 * @author kallifeinberg
 * @version 1.0
 */
public class Haversine implements Distance {

  /**
   * Computes the Haversine distance between two objects.
   *
   * @param coords1 The coordinates of the first object.
   * @param coords2 the coordinates of the second object.
   * @return The Haversine distance between the two objects.
   */
  @Override
  public double getDistance(double[] coords1, double[] coords2) {

    final int radius = 6371; // Radius of the earth

    Double lat1 = coords1[0];
    Double lon1 = coords1[1];
    Double lat2 = coords2[0];
    Double lon2 = coords2[1];

    Double latDistance = toRadians(lat2 - lat1);
    Double lonDistance = toRadians(lon2 - lon1);

    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(toRadians(lat1))
        * Math.cos(toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return radius * c;
  }

  private Double toRadians(Double value) {

    final int radians = 180; // Radius of the earth
    return value * Math.PI / radians;
  }

}

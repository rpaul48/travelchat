package edu.brown.cs.student.api.graph;

/**
 * This class is used to hold an IVertex and a Double that represents the
 * current best estimate for the lowest cost to reach that vertex.
 *
 * @param <V> - A class that extends IVertex and represents the type of the
 *            graph vertices
 * @param <E> - A class that extends IEdge of type V and represents the type of
 *            the graph edges
 */
public class VertexDistanceTuple<V extends IVertex<V, E>, E extends IEdge<V, E>> {

  /**
   * These are fields for this class.
   *
   * vertex - an object of type V, which represents the vertex of this tuple
   *
   * distance - a Double, which represents the current best estimate for the
   * lowest cost to reach that vertex
   */
  private V vertex;
  private Double distance;

  /**
   * This method is a constructor for the VertexDistanceTuple class.
   *
   * @param vert - an object of type V, which represents the vertex of this
   *             tuple
   * @param dist - a Double, which represents the current best estimate for the
   *             lowest cost to reach that vertex
   */
  public VertexDistanceTuple(V vert, Double dist) {
    vertex = vert;
    distance = dist;
  }

  /**
   * This method is used to get the vertex field of this object.
   *
   * @return an object of type V, which represents the vertex of this
   *         VertexDistanceTuple
   */
  public V getVertex() {
    return vertex;
  }

  /**
   * This method is used to get the distance field of this object.
   *
   * @return a Double, which represents the current best estimate for the lowest
   *         cost to reach that vertex
   */
  public Double getDistance() {
    return distance;
  }
}

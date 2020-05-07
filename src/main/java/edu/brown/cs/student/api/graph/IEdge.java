package edu.brown.cs.student.api.graph;

/**
 * This interface represents an edge of a weighted, directed graph.
 *
 * @param <V> - A class that extends IVertex and represents the type of the
 *            graph vertices
 * @param <E> - A class that extends IEdge of type V and represents the type of
 *            the graph edges
 */
public interface IEdge<V extends IVertex<V, E>, E extends IEdge<V, E>> {

  /**
   * This method returns the weight of the edge.
   *
   * @return a double, which represents the weight of the edge
   */
  double getWeight();

  /**
   * This method returns the start vertex of the edge.
   *
   * @return an object of type V, which represents the start vertex of this edge
   */
  V getStartVertex();

  /**
   * This method returns the end vertex of the edge.
   *
   * @return an object of type V, which represents the end vertex of this edge
   */
  V getEndVertex();
}

package edu.brown.cs.student.api.graph;

import java.util.List;

/**
 * This interface represents a vertex of a weighted, directed graph.
 *
 * @param <V> - A class that extends IVertex and represents the type of the
 *            graph vertices
 * @param <E> - A class that extends IEdge of type V and represents the type of
 *            the graph edges
 */
public interface IVertex<V extends IVertex<V, E>, E extends IEdge<V, E>> {

  /**
   * This method gets a List of objects of type E, which represents the edges
   * that leave this vertex.
   *
   * @return a List of objects of type E, which represents the edges that leave
   *         this vertex, or null if an error occurred
   */
  List<E> getEdges();

  /**
   * This method gets the heuristic between two nodes.
   *
   * @param node1 the first node
   * @param node2 the second node
   *
   * @return a double representing the heuristic between the two given nodes
   */
  double getHeuristic(V node1, V node2);
}

package edu.brown.cs.student.plan_my_day.shortest_path_finder;

import java.util.List;

/**
 * A shortest-path finder for generalized graphs; Strategy pattern.
 *
 * @param <V> The vertex that this finder will process.
 * @param <E> The edge that this finder will process.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public interface ShortestPathFinder<V, E> {

  /**
   * Finds the shortest path in a graph, storing the traversed nodes.
   *
   * @param start vertex of path
   * @param end   vertex of graph
   */
  void findShortestPath(V start, V end);

  /**
   * Prints shortest-path, starting from first in path. If no connection was
   * possible, prints an error string.
   *
   * @return A string representation of the shortest-path.
   */
  String printShortestPath();

  /**
   * Returns shortest path.
   */
  List<V> getShortestPath();
}

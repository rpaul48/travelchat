package edu.brown.cs.student.plan_my_day.graph;

import java.util.List;

/**
 * A generalized vertex.
 * @param <E> The type of edge that this vertex is associated with.
 * @author Kuba Tarlowski and Joshua Nathan Mugerwa
 * @version 1.1
 */
public interface Vertex<E> {
  /**
   * Getter of the vertex's ID.
   *
   * @return The vertex's ID.
   */
  String getID();

  /**
   * Getter of the vertex's edge set.
   *
   * @return The vertex's edge set.
   */
  List<E> getEdges();

  /**
   * Getter of vertex's name.
   *
   * @return The vertex's name.
   */
  String getName();


  /**
   * Adds an edge to this vertex's edge set.
   * @param edge to be added
   */
  void addEdge(E edge);

  /**
   * Removes an edge from the node's edge set.
   * @param edge to be removed
   */
  void removeEdge(E edge);
}

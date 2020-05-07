package edu.brown.cs.student.api.graph;

import java.util.Comparator;

/**
 * This Comparator is used to compare VertexDistanceTuples by their distance
 * fields. It implements Comparator of type VertexDistanceTuple of type V and E.
 *
 * @param <V> - an object of type V representing an IVertex
 * @param <E> - an object of type E representing an IEdge
 */
public class VertexDistanceTupleComparator<V extends IVertex<V, E>, E extends IEdge<V, E>>
    implements Comparator<VertexDistanceTuple<V, E>> {

  private V targetNode;

  /**
   * This is a constructor for the VertexDistanceTupleComparator class.
   *
   * @param targetNode - an object of type V representing the node to which the
   *                   distance needs to be compared
   */
  public VertexDistanceTupleComparator(V targetNode) {
    this.targetNode = targetNode;
  }

  @Override
  public int compare(VertexDistanceTuple<V, E> tuple1,
      VertexDistanceTuple<V, E> tuple2) {
    return Double.compare(
        tuple1.getDistance()
            + tuple1.getVertex().getHeuristic(tuple1.getVertex(), targetNode),
        tuple2.getDistance()
            + tuple2.getVertex().getHeuristic(tuple2.getVertex(), targetNode));
  }
}

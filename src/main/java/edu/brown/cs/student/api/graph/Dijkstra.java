package edu.brown.cs.student.api.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * This class is used to run Dijkstra's Algorithm on a WeightedDirectedGraph.
 *
 * @param <V> - A class that extends IVertex and represents the type of the
 *            graph vertices
 * @param <E> - A class that extends IEdge of type V and represents the type of
 *            the graph edges
 */
public class Dijkstra<V extends IVertex<V, E>, E extends IEdge<V, E>> {

  /**
   * These are fields for this class.
   *
   * source - an object of type V, which represents the Vertex at which to start
   * the solution path
   *
   * distMap - a Map of type V, Double, where a vertex points to the current
   * best estimate for the lowest cost to reach that vertex
   *
   * vertexToEdgeFromParentMap - a Map of objects of type V to objects of type
   * E, where the E objects represent the edges that connect the key vertex with
   * the parent of that vertex in the current best path to that vertex
   */
  private V source;
  private Map<V, Double> distMap = new HashMap<>();
  private Map<V, E> vertexToEdgeFromParentMap = new HashMap<>();

  /**
   * This is a constructor for the Dijkstra class.
   *
   * @param sourceVertex - a vertex of type V from which the algorithm starts
   *                     its search
   */
  public Dijkstra(V sourceVertex) {
    source = sourceVertex;
  }

  /**
   * This method creates the path to the target vertex.
   *
   * @param targetVertex - an object of type V, which represents the target of
   *                     the path to be made
   *
   * @return a List of MovieEdges, which represents the path from the starting
   *         actor to the target actor, where the edges are in reverse order
   *         (the first edge goes from the target actor's parent to the target
   *         actor)
   */
  private List<E> makePath(V targetVertex) {
    V currVertex = targetVertex;
    E currEdge = vertexToEdgeFromParentMap.get(currVertex);
    List<E> path = new LinkedList<>();

    while (currEdge != null) {
      path.add(currEdge);
      currVertex = currEdge.getStartVertex();
      currEdge = vertexToEdgeFromParentMap.get(currVertex);
    }

    return path;
  }

  /**
   * This method uses Dijkstra's Algorithm to find the shortest path to the
   * given vertex.
   *
   * @param targetVertex - an object of type V, which represents the target
   *                     vertex to which we want a path
   *
   * @return a List of MovieEdges, which represents the path from the starting
   *         actor to the target actor, where the edges are in reverse order
   *         (the first edge goes from the target actor's parent to the target
   *         actor)!!
   */
  public List<E> dijkstra(V targetVertex) {
    // add the source to the priority queue and map
    VertexDistanceTupleComparator<V, E> comparator = new VertexDistanceTupleComparator<>(
        targetVertex);
    PriorityQueue<VertexDistanceTuple<V, E>> distPQ = new PriorityQueue<>(1,
        comparator);
    distMap.put(source, 0.0);
    VertexDistanceTuple<V, E> sourceTuple = new VertexDistanceTuple<>(source,
        0.0);
    distPQ.add(sourceTuple);
    vertexToEdgeFromParentMap.put(source, null);

    VertexDistanceTuple<V, E> currVertTuple = distPQ.poll();
    V currVert = currVertTuple.getVertex();
    double distToCurrVert = currVertTuple.getDistance();
    List<E> edgesFromCurr = currVert.getEdges();

    if (edgesFromCurr == null) {
      // an error occurred
      return null;
    }

    while ((currVert != null) && !currVert.equals(targetVertex)) {
      for (E edge : edgesFromCurr) {
        V neighbor = edge.getEndVertex();

        if (!distMap.containsKey(neighbor)) {
          // this node is new and needs to be added to the priority queue and
          // map
          distMap.put(neighbor, Double.POSITIVE_INFINITY);
          VertexDistanceTuple<V, E> newTuple = new VertexDistanceTuple<>(
              neighbor, Double.POSITIVE_INFINITY);
          distPQ.add(newTuple);
          vertexToEdgeFromParentMap.put(neighbor, null);
        }

        double prevDistToNeighbor = distMap.get(neighbor);
        double newEstimate = distToCurrVert + edge.getWeight();

        if (newEstimate < prevDistToNeighbor) {
          // update the best estimate to reach this node
          vertexToEdgeFromParentMap.put(neighbor, edge);
          distMap.replace(neighbor, newEstimate);
          VertexDistanceTuple<V, E> neighborTuple = new VertexDistanceTuple<>(
              neighbor, newEstimate);
          distPQ.offer(neighborTuple);
        }
      }

      // get the tuple that has the shortest best estimate path to reach its
      // vertex
      currVertTuple = distPQ.poll();

      if (currVertTuple == null) {
        return null;
      } else {
        currVert = currVertTuple.getVertex();
        distToCurrVert = currVertTuple.getDistance();
        edgesFromCurr = currVert.getEdges();
      }
    }

    return makePath(currVert);
  }
}

package edu.brown.cs.student.plan_my_day.graph;

import edu.brown.cs.student.plan_my_day.distance.Haversine;

/**
 * An edge between two actors.
 *
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class WayEdge implements Edge<ItemVertex> {

  private double weight;
  private String id;
  private ItemVertex start;
  private ItemVertex end;

  /**
   * Constructor for a way edge.
   *
   * @param start The starting (outgoing) vertex.
   * @param end   The ending (incoming) vertex.
   */
  public WayEdge(ItemVertex start, ItemVertex end) {
    if (start != null && end != null) {
      this.weight = new Haversine().getDistance(start.getCoordinates(), end.getCoordinates());
    }
    else {
      System.out.println(String.format("WARNING: the follow edge had a null value: %s -> %s", start.getName(),
                        end.getName()));
    }
    this.start = start;
    this.end = end;
  }

  /**
   * Accessor of the ID of the edge.
   *
   * @return Unique ID of the edge.
   */
  public String getID() {
    return id;
  }

  /**
   * Accessor for the weight of the edge.
   *
   * @return The weight of the edge.
   */
  @Override
  public double getWeight() {
    return weight;
  }

  /**
   * Accessor of the start of the edge.
   *
   * @return The start of the edge; a vertex.
   */
  @Override
  public ItemVertex getStart() {
    return start;
  }

  /**
   * Accessor of the end of the edge.
   *
   * @return The end of the edge; a vertex.
   */
  @Override
  public ItemVertex getEnd() {
    return end;
  }

  /**
   * Returns the vertex opposite of the vertex given in the edge.
   *
   * @param givenSideOfEdge The given side (i.e. vertex) of the edge.
   * @return The vertex opposite to the one given.
   */
  @Override
  public ItemVertex getOpposite(ItemVertex givenSideOfEdge) {
    if (givenSideOfEdge.equals(start)) {
      return end;
    }
    return start;
  }

  /**
   * Getter for the edge's label.
   *
   * @return The edge's label.
   */
  @Override
  public String getEdgeLabel() {
    return id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    WayEdge other = (WayEdge) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

}

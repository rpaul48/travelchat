package edu.brown.cs.student.plan_my_day.graph;

import edu.brown.cs.student.api.tripadvisor.objects.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A vertex model of a TripAdvisor location. This implementation allows for both shortest path and spatial search
 * algorithms.
 *
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class ItemVertex implements Vertex<WayEdge> {

    private double[] spatialCoordinate;
    private Item item;
    private List<WayEdge> edges;

    /**
     * Constructs a vertex using an array of coordinates (i.e. an array of
     * [latitude, longitude]).
     *
     * @param item
     */
    public ItemVertex(Item item) {
        this.item = item;
        this.spatialCoordinate = new double[]{item.getLatitude(), item.getLongitude()};
        this.edges = new ArrayList<>();
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Getter of the vertex's ID.
     *
     * @return The vertex's ID.
     */
    @Override
    public String getID() {
        return this.item.getName();
    }

    /**
     * Getter of the vertex's edge set (copied).
     *
     * @return The vertex's edge set.
     */
    @Override
    public List<WayEdge> getEdges() {
        return new ArrayList<>(edges);
    }

    /**
     * Setter of the vertex's edge set.
     *
     * @param edges
     */
    public void setEdges(List<WayEdge> edges) {
        this.edges = edges;
    }

    /**
     * Getter of vertex's name.
     *
     * @return The vertex's name.
     */
    @Override
    public String getName() {
        return this.item.getName();
    }

    /**
     * Adds an edge to this vertex's edge set.
     */
    @Override
    public void addEdge(WayEdge edge) {
        this.edges.add(edge);
    }

    /**
     * Removes an edge from the node's edge set.
     */
    @Override
    public void removeEdge(WayEdge edge) {
        edges.remove(edge);
    }

    /**
     * Getter of number of spatial coordinates.
     *
     * @return The number of spatial coordinates.
     */
    public int getDimensions() {
        return this.spatialCoordinate.length;
    }

    /**
     * Getter of spatial coordinate.
     *
     * @return The spatial coordinate of the location.
     */
    public double[] getCoordinates() {
        return Arrays.copyOf(spatialCoordinate, spatialCoordinate.length);
    }

    /**
     * Equality override; two ActorVertices are equal iff they have the same id and
     * name.
     *
     * @param o The other vertex.
     * @return True iff the two vertices equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemVertex other = (ItemVertex) o;
        return this.getID().equals(other.getID()) && (Arrays.equals(this.spatialCoordinate, other
                .getCoordinates()));
    }

    /**
     * Override of hashing, based on id.
     *
     * @return The hashcode of the vertex.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getID());
    }

    @Override
    public String toString() {
        return String.format("Vertex representing: \n Item type: %s \n Name: %s \n Location: %s",
                this.getItem().getClass(), this.getName(), this.getItem().getLocationString());
    }
}

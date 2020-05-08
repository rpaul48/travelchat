package edu.brown.cs.student.path_finder.graph;

import java.util.*;

import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;


/**
 * This class builds the graph using restaurants and attractions Items that get
 * stored in GraphNodes to be used for PlanMyDay Feature.
 *
 */
public class PlanMyDayGraph {
  private List<Restaurant> restaurants; // containing 3 Restaurants picked
  private List<ItemVertex> restaurantsAsNodes;
  private List<Attraction> attractions;

  /**
   * Constructor.
   *
   * @param restaurants - list of three Restaurants picked
   * @param attractions - list of all possible Attractions
   */
  public PlanMyDayGraph(List<Restaurant> restaurants, List<Attraction> attractions) {
    this.restaurants = restaurants;
    this.attractions = attractions;
    this.restaurantsAsNodes = new LinkedList<>();
  }

  /**
   * Constructs a graph of restaurants and attractions.
   * @return A map representation of the graph; G = (V, E) where V = keySet and E = valueSet
   */
  public Map<ItemVertex, List<WayEdge>> buildGraph() {
    Map<ItemVertex, List<WayEdge>> map = new HashMap<>();
    for (int i = 0; i < attractions.size(); i++) { //
      Attraction attr = attractions.get(i);
      List<WayEdge> edgesList = new ArrayList<WayEdge>();
      ItemVertex attractionNode1 = new ItemVertex(attr);

      for (int j = 0; j < restaurants.size(); j++) {
        Restaurant rest = restaurants.get(j);
        ItemVertex restaurantNode = new ItemVertex(rest);
        restaurantsAsNodes.add(restaurantNode);
        WayEdge edge = new WayEdge(attractionNode1, restaurantNode);
        edgesList.add(edge);
      }

      for (int j = 0; j < attractions.size(); j++) { // Avoids creating edges between self
        if (i == j) {
          continue;
        }

        Attraction attr2 = attractions.get(j);
        ItemVertex attractionNode2 = new ItemVertex(attr2);
        WayEdge edge = new WayEdge(attractionNode1, attractionNode2);
        edgesList.add(edge);
      }

      map.put(attractionNode1, edgesList);
      attractionNode1.setEdges(edgesList);
    }

    return map;
  }

  /**
   * Getter of List of Restaurant nodes.
   *
   * @return List of Restaurants nodes.
   */
  public List<ItemVertex> getRestaurantsAsNodes() {
    return restaurantsAsNodes;
  }

  /**
   * Setter of List of Restaurant nodes.
   *
   * @param restaurantsAsNodes of nodes to set to.
   */
  public void setRestaurantsAsNodes(List<ItemVertex> restaurantsAsNodes) {
    this.restaurantsAsNodes = restaurantsAsNodes;
  }

  /**
   * Getter of List of Restaurants.
   *
   * @return List of Restaurants.
   */
  public List<Restaurant> getRestaurants() {
    return restaurants;
  }

  /**
   * Getter of List of Attractions.
   *
   * @return List of Attractions.
   */
  public List<Attraction> getAttractions() {
    return attractions;
  }

  /**
   * Setter of List of Restaurants.
   *
   * @param restaurants of Restaurants to newly set to.
   */
  public void setRestaurants(List<Restaurant> restaurants) {
    this.restaurants = restaurants;
  }

  /**
   * Setter of List of Attractions.
   *
   * @param attractions of Attractions to newly set to.
   */
  public void setAttractions(List<Attraction> attractions) {
    this.attractions = attractions;
  }
}

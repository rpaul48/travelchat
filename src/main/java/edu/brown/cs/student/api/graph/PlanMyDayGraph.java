package edu.brown.cs.student.api.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;

/**
 * This class builds the graph using restaurants and attractions Items that get
 * stored in GraphNodes to be used for PlanMyDay Feature.
 *
 */
public class PlanMyDayGraph {
  private List<Restaurant> restaurants; // containing 3 Restaurants picked
  private List<Attraction> attractions;
  private final double EARTH_RADIUS = 6371.0;

  public PlanMyDayGraph(List<Restaurant> restaurants, List<Attraction> attractions) {
    this.restaurants = restaurants;
    this.attractions = attractions;
  }

  public Map<GraphNode, List<WayEdge>> buildGraph() {
    Map<GraphNode, List<WayEdge>> map = new HashMap<GraphNode, List<WayEdge>>();
    for (int i = 0; i < attractions.size(); i++) {
      Attraction attr = attractions.get(i);
      List<WayEdge> edgesList = new ArrayList<WayEdge>();
      GraphNode node1 = new GraphNode(attr, attr.getLatitude(), attr.getLongitude(), null);

      for (int j = 0; j < restaurants.size(); j++) {
        Restaurant rest = restaurants.get(j);
        GraphNode node2 = new GraphNode(rest, rest.getLatitude(), rest.getLongitude(), null);
        WayEdge edge = new WayEdge(node1, node2);
        edgesList.add(edge);
      }

      for (int j = 0; j < attractions.size(); j++) {
        if (i == j) {
          continue;
        }

        Attraction attr2 = attractions.get(j);
        GraphNode node2 = new GraphNode(attr2, attr2.getLatitude(), attr2.getLongitude(), null);
        WayEdge edge = new WayEdge(node1, node2);
        edgesList.add(edge);
      }

      map.put(node1, edgesList);
      node1.setEdges(edgesList);
    }

    return map;
  }

  public List<Restaurant> getRestaurants() {
    return restaurants;
  }

  public List<Attraction> getAttractions() {
    return attractions;
  }

  public void setRestaurants(List<Restaurant> restaurants) {
    this.restaurants = restaurants;
  }

  public void setAttractions(List<Attraction> attractions) {
    this.attractions = attractions;
  }

  /**
   * This method calculates the Haversine distance between two nodes.
   *
   * @param node1 GraphNode
   * @param node2 GraphNode
   * @return Haversine distance between two nodes
   */
  public double calculateDistance(GraphNode node1, GraphNode node2) {
    double lat1 = node1.getLat();
    double lon1 = node1.getLon();
    double lat2 = node2.getLat();
    double lon2 = node2.getLon();
    double radLat1 = Math.toRadians(lat1);
    double radLat2 = Math.toRadians(lat2);
    double totalLat = Math.toRadians(lat2 - lat1);
    double totalLon = Math.toRadians(lon2 - lon1);

    double transitory = Math.pow(Math.sin(totalLat / 2.0), 2.0)
        + (Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(totalLon / 2.0), 2.0));
    double distance = (2.0 * EARTH_RADIUS * Math.asin(Math.sqrt(transitory)));
    return distance;
  }
}

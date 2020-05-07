//package edu.brown.cs.student.api.graph;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
//import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;
//
//public class PlanMyDayGraph {
//  private List<Restaurant> restaurants; // containing 3 Restaurants picked
//  private List<Attraction> attractions;
//  private final double EARTH_RADIUS = 6371.0;
//
//  public PlanMyDayGraph(List<Restaurant> restaurants, List<Attraction> attractions) {
//    this.restaurants = restaurants;
//    this.attractions = attractions;
//  }
//
//  public List<WayEdge> buildGraph() {
//    for (int i = 0; i < attractions.size(); i++) {
//      Attraction attr = attractions.get(i);
//      List<WayEdge> edgesList = new ArrayList<WayEdge>();
//      GraphNode node1 = new GraphNode(attr, attr.getLatitude(), attr.getLongitude(), null);
//      for (int j = 0; j < restaurants.size(); j++) {
//        Restaurant rest = restaurants.get(j);
//        GraphNode node2 = new GraphNode(rest, attr.getLatitude(), attr.getLongitude(), null);
//        WayEdge edge = new WayEdge(this.calculateDistance(node1, node2), node1, node2);
//      }
//
//    }
//    return null;
//  }
//
//  /**
//   * This method calculates the Haversine distance between two nodes.
//   *
//   * @param node1 GraphNode
//   * @param node2 GraphNode
//   * @return Haversine distance between two nodes
//   */
//  public double calculateDistance(GraphNode node1, GraphNode node2) {
//    double lat1 = node1.getLat();
//    double lon1 = node1.getLon();
//    double lat2 = node2.getLat();
//    double lon2 = node2.getLon();
//    double radLat1 = Math.toRadians(lat1);
//    double radLat2 = Math.toRadians(lat2);
//    double totalLat = Math.toRadians(lat2 - lat1);
//    double totalLon = Math.toRadians(lon2 - lon1);
//
//    double transitory = Math.pow(Math.sin(totalLat / 2.0), 2.0)
//        + (Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(totalLon / 2.0), 2.0));
//    double distance = (2.0 * EARTH_RADIUS * Math.asin(Math.sqrt(transitory)));
//    return distance;
//  }
//}

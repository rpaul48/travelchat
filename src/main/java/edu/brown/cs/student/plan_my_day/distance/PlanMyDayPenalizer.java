package edu.brown.cs.student.plan_my_day.distance;

import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;
import edu.brown.cs.student.plan_my_day.graph.ItemVertex;

/**
 * A penalty term for A* search within the Maps program.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class PlanMyDayPenalizer implements Penalizer<ItemVertex> {
    private double distWeight;
    private double priceWeight;

    /**
     * Assigns penalty weights for price and distance-from-source of an attraction.
     * @param distWeight
     * @param priceWeight
     */
    public PlanMyDayPenalizer(double distWeight, double priceWeight) {
        this.distWeight = distWeight;
        this.priceWeight = priceWeight;
    }

    /**
     * Computes the penalty for a LocationVertex.
     * @param v1 The first vertex.
     * @param v2 The second vertex.
     * @return The penalty; the Haversine distance between the two verices
     */
    @Override
    public double computePenalty(ItemVertex v1, ItemVertex v2) {
        if (v2.getItem() instanceof Restaurant) { // There is no penalty for traveling to a restaurant.
            return 0.0;
        }
        double dist = new Haversine().getDistance(v1.getCoordinates(),
                     v2.getCoordinates());
        Attraction attraction = (Attraction) v2.getItem();
        double price = attraction.getLowestPrice();
        return distWeight * dist + priceWeight * price;
    }
}

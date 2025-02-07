package com.abdulbasit.flypath.utils;

import com.abdulbasit.flypath.model.Flight;
import com.abdulbasit.flypath.model.Itinerary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class FlightGraph {



    PriorityQueue<Itinerary> itineraries;

    public FlightGraph() {
        this(true);
    }

    @Autowired(required = false)
    public FlightGraph(boolean cheapest){
        if (!cheapest){
            itineraries = new PriorityQueue<>((a,b)->{
                int durationComparison = Integer.compare(a.getDurationInt(),b.getDurationInt());

                if (durationComparison==0){
                    int priceComparison = Integer.compare(Integer.parseInt(a.getPrice()),Integer.parseInt(b.getPrice()));
                    if (priceComparison==0){
                        return Integer.compare(a.getRoutes().size(),b.getRoutes().size());
                    }
                    return priceComparison;
                }
                return durationComparison;
            });

        }else{
            itineraries = new PriorityQueue<>((a,b)->{
                int priceComparison = Integer.compare(Integer.parseInt(a.getPrice()),Integer.parseInt(b.getPrice()));
                if (priceComparison==0){
                    int durationComparison = Integer.compare(a.getDurationInt(),b.getDurationInt());
                    if (durationComparison==0){
                        return Integer.compare(a.getRoutes().size(),b.getRoutes().size());
                    }
                    return durationComparison;
                }
                return priceComparison;
            });
        }
    }



    // In FlightGraph.java
    public void addFlight(Itinerary itinerary) {
        itineraries.offer(itinerary);
//        if (itineraries.size()>5){
//            itineraries.poll();
//        }





    }

    public Itinerary findShortestRoute(String source, String destination) {
        return itineraries.peek();
    }

    public Itinerary findCheapestRoute(String source, String destination) {
            return itineraries.peek();
    }


}

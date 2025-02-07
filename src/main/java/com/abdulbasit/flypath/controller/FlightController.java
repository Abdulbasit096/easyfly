package com.abdulbasit.flypath.controller;

import com.abdulbasit.flypath.model.FlightRequest;
import com.abdulbasit.flypath.model.Itinerary;
import com.abdulbasit.flypath.model.RouteModel;
import com.abdulbasit.flypath.service.FlightService;
import com.abdulbasit.flypath.utils.FlightGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "http://localhost:3000")
public class FlightController {


    @Autowired
    FlightService flightService;




    @GetMapping("/search")
    public ResponseEntity<?> searchFlight(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam String departureDate,
            @RequestParam String returnDate,
            @RequestParam int adults,
            @RequestParam(required = false) Boolean cheapest
    ) {
        if (cheapest!=null){
            FlightRequest request = new FlightRequest(origin, destination, departureDate, returnDate, adults, cheapest);

            FlightGraph graph = flightService.loadFlightData(request);
            List<Itinerary> itineraries = new ArrayList<>();
            if (cheapest){
                itineraries.add(graph.findCheapestRoute(origin,destination));
            }else{
                itineraries.add(graph.findShortestRoute(origin,destination));
            }
            return  ResponseEntity.ok(itineraries);

        }else{
            FlightRequest request = new FlightRequest(origin, destination, departureDate, returnDate, adults, false);
            List<Itinerary> routes = flightService.getAllFlights(request);
            return ResponseEntity.ok(routes);

        }



    }



}

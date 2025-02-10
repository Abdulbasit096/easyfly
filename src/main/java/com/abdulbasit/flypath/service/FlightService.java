package com.abdulbasit.flypath.service;
import com.abdulbasit.flypath.model.FlightRequest;
import com.abdulbasit.flypath.model.Itinerary;
import com.abdulbasit.flypath.utils.FlightGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class FlightService {

    Logger logger = LoggerFactory.getLogger(FlightService.class);

    @Autowired
    AmadeusAPIService amadeusAPIService;


    public List<Itinerary> getAllFlights(FlightRequest request){
        return amadeusAPIService.searchFlights(request);
    }

    public FlightGraph loadFlightData(FlightRequest request) {
        List<Itinerary> itineraries = amadeusAPIService.searchFlights(request);
        FlightGraph flightGraph = new FlightGraph(request.cheapest());
        for (Itinerary itinerary : itineraries) {
            flightGraph.addFlight(itinerary);
        }

        return flightGraph;
    }



}

package com.abdulbasit.flypath.utils;

import com.abdulbasit.flypath.model.Flight;
import com.abdulbasit.flypath.model.Itinerary;
import com.abdulbasit.flypath.model.RouteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class Route {

    private final List<Flight> flights = new ArrayList<>();
    private double totalPrice = 0;
    private int totalDuration = 0;

    CurrencyConversion currencyConversion = new CurrencyConversion();





    public void addFlight(Flight flight) {
        flights.add(flight);
        totalPrice += flight.price();
        totalDuration += flight.duration();

    }

    // Getters
    public List<Flight>  getFlights() { return flights; }
    public double getTotalPrice() { return totalPrice; }

    public int getTotalDuration() { return totalDuration; }


    public RouteModel getRoute() {
        Itinerary itinerary = new Itinerary();
        RouteModel routeModel = new RouteModel();
        if (flights.isEmpty()){
            return null;
        }


        for (Flight flight : flights) {
            routeModel.addSegment(flight.airline().name(), flight.source(), flight.destination());
        }


        try {
            routeModel.setPrice(String.format("%.2f", currencyConversion.convertEurToPkr(totalPrice)));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }




        routeModel.setDurationFromMinutes(totalDuration);

        return routeModel;
    }
}

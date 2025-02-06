package com.abdulbasit.flypath.service;

import com.abdulbasit.flypath.model.Airport;
import com.abdulbasit.flypath.utils.AirportLoader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AirportService {

    Map<String, Airport> airports;

    @Autowired
    AirportLoader airportLoader;

    @PostConstruct
    void getAirports(){
        airports = airportLoader.getAirports();
    }





    public List<Airport> getAllAirports(){
        return airports.values().stream().toList();
    }

    public Airport getByIata(String iata){
        return airports.getOrDefault(iata,null);
    }

    public List<Airport> searchAirport(String searchTerm) {
        String search = searchTerm.toLowerCase();
        return airports.values().stream()
                .filter(airport ->
                        airport.country().toLowerCase().contains(search) ||
                                airport.city().toLowerCase().contains(search) ||
                                airport.airportName().toLowerCase().contains(search)
                )
                .collect(Collectors.toList());
    }
}

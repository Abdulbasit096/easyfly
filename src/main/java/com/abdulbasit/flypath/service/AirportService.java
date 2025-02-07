package com.abdulbasit.flypath.service;

import com.abdulbasit.flypath.model.Airport;
import com.abdulbasit.flypath.utils.AirportLoader;
import jakarta.annotation.PostConstruct;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
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

    Logger logger = LoggerFactory.getLogger(AirportService.class);





    public List<Airport> getAllAirports(){
        return airports.values().stream().toList();
    }

    public Airport getByIata(String iata){
        return airports.getOrDefault(iata,null);
    }


    @Cacheable(
            value = "airportSearchCache",
            key = "#searchTerm.toLowerCase()"
    )
    public List<Airport> searchAirport(String searchTerm) {
        logger.info("AHH searching bro");
        String search = searchTerm.toLowerCase();
        return airports.values().stream()
                .filter(airport ->
                        airport.country().toLowerCase().contains(search) ||
                                airport.city().toLowerCase().contains(search) ||
                                airport.IATA().equals(search) ||
                                airport.IATA().toLowerCase().contains(search) ||
                                airport.airportName().toLowerCase().contains(search)
                )
                .collect(Collectors.toList());
    }
}

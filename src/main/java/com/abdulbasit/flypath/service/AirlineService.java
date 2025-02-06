package com.abdulbasit.flypath.service;

import com.abdulbasit.flypath.model.Airline;
import com.abdulbasit.flypath.utils.AirlineLoader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AirlineService {


    Map<String, Airline> airlines;

    @Autowired
    AirlineLoader airlineLoader;

    @PostConstruct
    void loadAirlines(){
        airlines = airlineLoader.getAirlines();
    }



    public List<Airline> getAirlines(){
        return airlines.values().stream().toList();
    }

    public Airline getAirline(String carrierCode) {
        return airlines.get(carrierCode);
    }


}

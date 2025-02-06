package com.abdulbasit.flypath.controller;

import com.abdulbasit.flypath.model.Airline;
import com.abdulbasit.flypath.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {

    @Autowired
    AirlineService airlineService;


    @GetMapping("")
    public List<Airline> getAll(){
        return airlineService.getAirlines();
    }


    @GetMapping("/{iata}")
    public Airline getAll(@PathVariable String iata){
        return airlineService.getAirline(iata);
    }




}

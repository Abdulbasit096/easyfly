package com.abdulbasit.flypath.controller;


import com.abdulbasit.flypath.model.Airport;
import com.abdulbasit.flypath.service.AirportService;
import com.abdulbasit.flypath.utils.AirportLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@CrossOrigin(origins = "*")
public class AirportController {


    @Autowired
    AirportService airportService;


    @GetMapping("")
    public List<Airport> getAll(){
        return airportService.getAllAirports();
    }

    @GetMapping("/{iata}")
    public Airport getByIata(@PathVariable String iata){
        return airportService.getByIata(iata);
    }

    @GetMapping("/search/{searchTerm}")
    public  List<Airport> searchAirport(@PathVariable String searchTerm){
        return airportService.searchAirport(searchTerm);
    }

}

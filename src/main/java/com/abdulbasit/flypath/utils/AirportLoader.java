package com.abdulbasit.flypath.utils;


import com.abdulbasit.flypath.model.Airport;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class AirportLoader {



    final static Map<String, Airport> airports = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(AirportLoader.class);

    @PostConstruct
    private void loadAirports() {
        logger.info("Loading airports - START");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource("airports.csv").getInputStream()))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String iata = values[0];
                double lat = Double.parseDouble(values[4]);
                double lon = Double.parseDouble(values[5]);
                airports.put(iata, new Airport(iata,values[2],values[3],values[6],values[1],lat,lon));
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }finally {
            logger.info("Loaded {} airlines", airports.size());
        }
    }

    public Map<String, Airport> getAirports(){
        return airports;
    }



}

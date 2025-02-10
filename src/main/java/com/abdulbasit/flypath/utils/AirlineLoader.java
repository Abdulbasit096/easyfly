package com.abdulbasit.flypath.utils;


import com.abdulbasit.flypath.model.Airline;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AirlineLoader {

    private final Map<String, Airline> airlines = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(AirlineLoader.class);



    public AirlineLoader() {
        logger.info("Loading airlines - START");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource("airlines.csv").getInputStream()))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String iata = values[1];
                airlines.put(iata, new Airline(values[0],values[1]));
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }finally {
            logger.info("Loaded {} airlines", airlines.size());
        }
    }

    public Map<String,Airline> getAirlines(){
        return airlines;
    }


}

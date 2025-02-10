package com.abdulbasit.flypath.model;

import java.io.Serializable;

public record Airport(
        String IATA,
        String country,
        String city,
        String countryCode,
        String airportName,
        double latitude,
        double longitude
) implements Serializable {}


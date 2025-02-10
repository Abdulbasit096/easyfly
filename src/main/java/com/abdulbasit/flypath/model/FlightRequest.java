package com.abdulbasit.flypath.model;

import java.io.Serializable;

public record FlightRequest(
        String origin,
        String destination,
        String departureDate,
        String returnDate,
        int adults,
        boolean cheapest
) implements Serializable {}

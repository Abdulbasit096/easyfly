package com.abdulbasit.flypath.model;

public record FlightRequest(String origin,String destination,String departureDate,String returnDate,int adults,boolean cheapest) {}

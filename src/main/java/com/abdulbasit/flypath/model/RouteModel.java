package com.abdulbasit.flypath.model;

import jdk.jfr.Enabled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;





public class RouteModel {

    public static class Segment{
        String airline;
        String origin;
        String destination;

        public Segment(String airline, String origin, String destination) {
            this.airline = airline;
            this.origin = origin;
            this.destination = destination;
        }

        public String getAirline() {
            return airline;
        }

        public void setAirline(String airline) {
            this.airline = airline;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }
    }

    List<Segment> segments;
    String duration;
    String price;
    boolean isReturn;

    public RouteModel(){
        segments = new ArrayList<>();
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public void addSegment(String airline,String destination,String origin){
        this.segments.add(new Segment(airline,origin,destination));
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        int hours = duration / 60;
        int minutes = duration % 60;

        this.duration = String.format("%02d Hours and %02d Minutes", hours, minutes);
    }

    public void setPrice(String price){
        this.price = price;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }
}




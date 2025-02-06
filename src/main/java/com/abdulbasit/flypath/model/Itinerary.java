package com.abdulbasit.flypath.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Itinerary {

    List<RouteModel> routes;
    String price;
    @JsonIgnore
    private int durationInt;
    @JsonIgnore
    private int departureDurationInt;
    @JsonIgnore
    private int returnDurationInt;
    String totalDuration;
    String departureDuration;
    String returnDuration;


    public Itinerary(){
        routes = new ArrayList<>();
        durationInt =0;
    }

    public void addRoute(RouteModel routeModel){
        routes.add(routeModel);
        if (routeModel.isReturn){
            setReturnDuration(routeModel.getDuration());
        }else{
            setDepartureDuration(routeModel.getDuration());
        }

    }

    public List<RouteModel> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteModel> routes) {
        this.routes = routes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setDurationInt(int durationInt){
        this.durationInt += durationInt;
        setTotalDuration();
    }

    public int getDurationInt() {
        return durationInt;
    }

    public void setTotalDuration() {
        int hours = this.durationInt / 60;
        int minutes = this.durationInt % 60;

        this.totalDuration = String.format("%02d Hours and %02d Minutes", hours, minutes);
    }

    public String getDepartureDuration() {
        return departureDuration;
    }

    public void setDepartureDuration(String departureDuration) {
        this.departureDuration = departureDuration;
    }

    public String getReturnDuration() {
        return returnDuration;
    }

    public void setReturnDuration(String returnDuration) {
        this.returnDuration = returnDuration;
    }

    public int getDepartureDurationInt() {
        return departureDurationInt;
    }

    public void setDepartureDurationInt(int departureDurationInt) {
        this.departureDurationInt = departureDurationInt;
    }

    public int getReturnDurationInt() {
        return returnDurationInt;
    }

    public void setReturnDurationInt(int returnDurationInt) {
        this.returnDurationInt = returnDurationInt;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }
}

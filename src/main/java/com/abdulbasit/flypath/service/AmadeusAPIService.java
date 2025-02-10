package com.abdulbasit.flypath.service;

import com.abdulbasit.flypath.model.FlightRequest;
import com.abdulbasit.flypath.model.Itinerary;
import com.abdulbasit.flypath.model.RouteModel;
import com.abdulbasit.flypath.utils.CurrencyConversion;
import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.google.common.util.concurrent.RateLimiter;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AmadeusAPIService {
    Dotenv dotenv;
    private final Amadeus amadeus;
    Logger logger = LoggerFactory.getLogger(AmadeusAPIService.class);
    private final RateLimiter rateLimiter = RateLimiter.create(2.0);

    @Autowired
    AirlineService airlineService;


    public AmadeusAPIService() {
        dotenv = Dotenv.load();
        this.amadeus = Amadeus.builder(dotenv.get("API_KEY"), dotenv.get("API_SECRET")).build();
    }

    @Cacheable(
            value = "flightSearchCache",
            key = "#request.origin() + '_' + #request.destination() + '_' + " +
                    "#request.departureDate() + '_' + #request.returnDate() + '_' + " +
                    "#request.adults()"
    )
    public List<Itinerary> searchFlights(FlightRequest request) {
        rateLimiter.acquire();
        logger.info("Searching flights with params: origin={}, dest={}, date={}",
                request.origin(), request.destination(), request.departureDate());
        List<Itinerary> itineraries = new ArrayList<>();
        CurrencyConversion currencyConversion = new CurrencyConversion();

        try {
            FlightOfferSearch[] flightOffers = amadeus.shopping.flightOffersSearch.get(
                    Params.with("originLocationCode", request.origin())
                            .and("destinationLocationCode", request.destination())
                            .and("departureDate", request.departureDate())
                            .and("returnDate", request.returnDate())
                            .and("adults", request.adults())
                            .and("max", 100)
            );

            for (FlightOfferSearch offer : flightOffers) {
                Itinerary myItinerary = new Itinerary();
                int totalPrice = currencyConversion.convertEurToPkr(Double.parseDouble(offer.getPrice().getTotal()));
                myItinerary.setPrice(String.valueOf(totalPrice));


                for (FlightOfferSearch.Itinerary itinerary : offer.getItineraries()) {
                    RouteModel routeModel = new RouteModel();
                    int totalDuration = parseDuration(itinerary.getDuration());
                    routeModel.setDurationFromMinutes(totalDuration);
                    myItinerary.setDurationInt(totalDuration);
                    if (itinerary.getSegments()[0].getDeparture().getIataCode().equals(request.destination())){
                        routeModel.setReturn(true);
                        myItinerary.setReturnDurationInt(totalDuration);
                    }else {
                        myItinerary.setDepartureDurationInt(totalDuration);
                    }

                    for (FlightOfferSearch.SearchSegment segment : itinerary.getSegments()) {

                        routeModel.addSegment(airlineService.getAirline(segment.getCarrierCode()).name(),segment.getArrival().getIataCode(),segment.getDeparture().getIataCode());



                    }

                    myItinerary.addRoute(routeModel);
                }
                itineraries.add(myItinerary);
            }
        } catch (ResponseException  e) {
            logger.error(e.getMessage());
            logger.error("AMADEUS");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return itineraries;
    }
    private int parseDuration(String duration) {
        // Remove PT prefix
        duration = duration.substring(2);

        int hours = 0;
        int minutes = 0;

        // Find hours
        int hIndex = duration.indexOf('H');
        if (hIndex != -1) {
            hours = Integer.parseInt(duration.substring(0, hIndex));
            duration = duration.substring(hIndex + 1);
        }

        // Find minutes
        int mIndex = duration.indexOf('M');
        if (mIndex != -1) {
            minutes = Integer.parseInt(duration.substring(0, mIndex));
        }

        return hours * 60 + minutes;  // Return total minutes
    }

}

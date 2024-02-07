package org.example.model.airport.flight;

import org.example.model.BaseModel;
import org.example.model.airport.Airport;

public interface Flight extends BaseModel {

    Airport getSource();
    Airport getDestination();
    // Airport that Flight returns to
    Airport getHomeBase();
    int getFlightNumber();
    int getDay();
    boolean isFull();
}

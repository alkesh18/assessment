package org.example.model.order;

import org.example.model.BaseModel;

public interface Order extends BaseModel {

    String getOrderIdentifier();

    String getDestination();

    String getSource();

    int getShippingDate();
    boolean isShipped();
    int getShippingFlight();
}

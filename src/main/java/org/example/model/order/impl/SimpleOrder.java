package org.example.model.order.impl;

import org.example.model.order.Order;

public class SimpleOrder implements Order {

    private String destination;
    private String source;
    private int shippingDate;
    private final String orderIdentifier;
    private boolean isShipped = false;
    private int shippingFlight;

    public SimpleOrder(String orderIdentifier, String destination) {
        this.orderIdentifier = orderIdentifier;
        this.destination = destination;
    }

    @Override
    public String getOrderIdentifier() {
        return this.orderIdentifier;
    }

    @Override
    public String getDestination() {
        return this.destination;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public int getShippingDate() {
        return this.shippingDate;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setShippingDate(int shippingDate) {
        this.shippingDate = shippingDate;
    }

    @Override
    public boolean isShipped() {
        return this.isShipped;
    }

    public void setShipped(boolean shipped) {
        this.isShipped = shipped;
    }

    @Override
    public int getShippingFlight() {
        return shippingFlight;
    }

    public void setShippingFlight(int shippingFlight) {
        this.shippingFlight = shippingFlight;
    }

    @Override
    public String toString() {
        if(isShipped()) {
            return "order: " + getOrderIdentifier() + ", flightNumber: " + getShippingFlight() + ", departure: " + getSource() + ", arrival: " + getDestination() + ", day: " + getShippingDate();
        } else {
            return "order: " + getOrderIdentifier() + ", flightNumber: not scheduled";
        }
    }
}

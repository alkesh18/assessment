package org.example.model.airport.flight.impl.cargo;

import org.example.model.airport.Airport;
import org.example.model.airport.flight.Flight;
import org.example.model.order.Order;

import java.util.List;

public class CargoFlight implements Flight {

    protected Airport source;
    protected Airport destination;
    protected Airport homeBase;
    protected int priority;
    protected int flightNumber;
    protected int day;

    protected List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setSource(Airport source) {
        this.source = source;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public void setHomeBase(Airport homeBase) {
        this.homeBase = homeBase;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public Airport getSource() {
        return this.source;
    }

    @Override
    public Airport getDestination() {
        return this.destination;
    }

    @Override
    public Airport getHomeBase() {
        return this.homeBase;
    }

    @Override
    public int getFlightNumber() {
        return this.flightNumber;
    }

    @Override
    public int getDay() {
        return this.day;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    public boolean addOrder(Order order) {
        return false;
    }

    public boolean addOrders(List<Order> orders) {
        return false;
    }

}

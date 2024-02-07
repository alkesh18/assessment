package org.example.model.airport.flight.impl.cargo.fixed;

import org.example.model.airport.flight.impl.cargo.CargoFlight;
import org.example.model.airport.impl.SimpleAirport;
import org.example.model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class FixedCapacityCargoFlight extends CargoFlight {

    private final int orderCapacity;

    public FixedCapacityCargoFlight(int orderCapacity, String source, String destination) {
        this.orderCapacity = orderCapacity;
        this.orders = new ArrayList<Order>(orderCapacity);
        this.source = new SimpleAirport(source);
        this.destination = new SimpleAirport(destination);
    }

    public FixedCapacityCargoFlight(int orderCapacity, String source, String destination, int day, int flightNumber) {
        this.orderCapacity = orderCapacity;
        this.orders = new ArrayList<Order>(orderCapacity);
        this.source = new SimpleAirport(source);
        this.destination = new SimpleAirport(destination);
        this.day = day;
        this.flightNumber = flightNumber;
    }

    public int getOrderCapacity() {
        return orderCapacity;
    }

    public boolean isFull() {
        return getOrders().size() >= getOrderCapacity();
    }

    @Override
    public boolean addOrder(Order order) {
        if (isFull()) {
            System.out.println("Cannot add anymore orders to flight");
            return false;
        }

        getOrders().add(order);
        return true;
    }

    @Override
    public boolean addOrders(List<Order> orders) {
        // orders list full or if size of order list to be added is more than room left in current list of orders
        if (isFull() || (orders.size() > (getOrderCapacity() - getOrders().size()))) {
            System.out.println("Cannot add anymore orders to flight");
            return false;
        }
        getOrders().addAll(orders);
        return true;
    }

    @Override
    public String toString() {
        return "Flight: " + getFlightNumber() + ", departure: " + getSource().getCode() + ", arrival: " + getDestination().getCode() + ", day: " + getDay();
    }
}

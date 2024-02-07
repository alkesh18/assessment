package org.example.service.impl;

import org.example.model.airport.flight.impl.cargo.CargoFlight;
import org.example.model.airport.flight.impl.cargo.fixed.FixedCapacityCargoFlight;
import org.example.model.order.Order;
import org.example.model.order.impl.SimpleOrder;
import org.example.model.parser.impl.SimpleOrderParser;
import org.example.service.BaseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class does the heavy lifting in terms of processing the itinerary, adding flights, printing data, etc.
 **/
public class OrderSchedulingService implements BaseService {

    private Map<String, List<CargoFlight>> availableFlights, fullFlights;
    private List<Order> unscheduledOrders;
    private List<FixedCapacityCargoFlight> absoluteFlights; // keep track of overall list of flights
    private List<SimpleOrder> absoluteOrders; // keep track of overall list of orders
    private String file = "src/main/java/org/example/data/coding-assigment-orders.json";
    private SimpleOrderParser simpleOrderParser;

    public OrderSchedulingService() {
        availableFlights = new HashMap<>();
        fullFlights = new HashMap<>();
        absoluteFlights = new ArrayList<>();
        absoluteOrders = new ArrayList<>();
        unscheduledOrders = new ArrayList<>();
        simpleOrderParser = new SimpleOrderParser();

        List<CargoFlight> torontoFlights = new ArrayList<>();
        List<CargoFlight> calgaryFlights = new ArrayList<>();
        List<CargoFlight> vancouverFlights = new ArrayList<>();

        generateDefaultSchedule(torontoFlights, calgaryFlights, vancouverFlights);

        availableFlights.put("YYZ", torontoFlights);
        availableFlights.put("YYC", calgaryFlights);
        availableFlights.put("YVR", vancouverFlights);

        absoluteOrders = loadOrderDataFromJson();
    }

    public boolean addCargoFlight(CargoFlight flight) {
        if (flight == null) {
            return false;
        }

        if (flight.getSource() == null || flight.getSource().getCode().isBlank()) {
            return false;
        }

        if (flight.getDestination() == null || flight.getDestination().getCode().isBlank()) {
            return false;
        }

        if (flight.getDay() <= 0) {
            return false;
        }

        if(flight.getFlightNumber() <= 0) {
            return false;
        }

        String source = flight.getSource().getCode();
        String destination = flight.getDestination().getCode();
        int day = flight.getDay();
        int flightNum = flight.getFlightNumber();

        FixedCapacityCargoFlight f = new FixedCapacityCargoFlight(20, source, destination, day, flightNum);

        List<CargoFlight> newList;

        if (availableFlights.containsKey(destination)) {
            newList = availableFlights.get(destination);
        } else {
            newList = new ArrayList<>();
        }

        newList.add(f);
        availableFlights.put(f.getDestination().getCode(), newList);

        absoluteFlights.add(f);

        return true;
    }

    public boolean scheduleOrder(SimpleOrder order) {
        //validation check
        if (order == null || order.getDestination() == null || order.getDestination().isBlank()) {
            System.out.println("Invalid data, cannot schedule");
            return false;
        }

        // not in available flights, therefore unscheduled
        if (!availableFlights.containsKey(order.getDestination())) {
            this.unscheduledOrders.add(order);
            return false;
        }

        List<CargoFlight> flights = availableFlights.get(order.getDestination());
        CargoFlight flight = flights.get(0);

        boolean success = flight.addOrder(order);
        order.setShipped(success);

        // if successfully shipped order, then update order info
        if(success) {
            order.setShippingFlight(flight.getFlightNumber());
            order.setSource(flight.getSource().getCode());
            order.setShippingDate(flight.getDay());

            // determine if flight is full or not, move from available flights to full flights accordingly
            if (flight.isFull()) {
                if(fullFlights.containsKey(flight.getDestination().getCode())) {
                    fullFlights.get(flight.getDestination().getCode()).add(flight);
                } else {
                    List<CargoFlight> newList = new ArrayList<>();
                    newList.add(flight);
                    fullFlights.put(flight.getDestination().getCode(), newList);
                }
                flights.remove(flight);
            }
        }

        if(availableFlights.get(order.getDestination()).isEmpty()) {
            availableFlights.remove(order.getDestination());
        }

        return success;
    }

    public void createItinerary() {
        for(SimpleOrder order : absoluteOrders) {
            scheduleOrder(order);
        }
    }

    public String getFlightSchedule() {
        StringBuilder sb = new StringBuilder();

        for(FixedCapacityCargoFlight flight : absoluteFlights) {
            sb.append(flight.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    public String getOrderSchedule() {
        StringBuilder sb = new StringBuilder();

        for(SimpleOrder order : absoluteOrders) {
            sb.append(order.toString());
            sb.append("\n");
        }

        return sb.toString();
    }


    public List<SimpleOrder> loadOrderDataFromJson() {
        List<SimpleOrder> orders = new ArrayList<>();
        try {
             orders = getSimpleOrderParser().parseFromJson(getFile());
        } catch (Exception e) {
            System.out.println("Exception occured when parsing JSON");
        }

        return orders;
    }

    // generate default flight schedule
    private void generateDefaultSchedule(List<CargoFlight> torontoFlights, List<CargoFlight> calgaryFlights, List<CargoFlight> vancouverFlights) {
        FixedCapacityCargoFlight yyz1 = new FixedCapacityCargoFlight(20, "YUL", "YYZ", 1, 1);
        FixedCapacityCargoFlight yyc1 = new FixedCapacityCargoFlight(20, "YUL", "YYC", 1,2);
        FixedCapacityCargoFlight yvr1 = new FixedCapacityCargoFlight(20, "YUL", "YVR", 1, 3);
        FixedCapacityCargoFlight yyz2 = new FixedCapacityCargoFlight(20, "YUL", "YYZ", 2, 4);
        FixedCapacityCargoFlight yyc2 = new FixedCapacityCargoFlight(20, "YUL", "YYC", 2, 5);
        FixedCapacityCargoFlight yvr2 = new FixedCapacityCargoFlight(20, "YUL", "YVR", 2, 6);

        torontoFlights.addAll(List.of(yyz1, yyz2));
        calgaryFlights.addAll(List.of(yyc1, yyc2));
        vancouverFlights.addAll(List.of(yvr1, yvr2));

        getAbsoluteFlights().addAll(List.of(yyz1, yyc1, yvr1, yyz2, yyc2, yvr2));
    }

    public Map<String, List<CargoFlight>> getAvailableFlights() {
        return availableFlights;
    }

    public void setAvailableFlights(Map<String, List<CargoFlight>> availableFlights) {
        this.availableFlights = availableFlights;
    }

    public Map<String, List<CargoFlight>> getFullFlights() {
        return fullFlights;
    }

    public void setFullFlights(Map<String, List<CargoFlight>> fullFlights) {
        this.fullFlights = fullFlights;
    }

    public List<Order> getUnscheduledOrders() {
        return unscheduledOrders;
    }

    public void setUnscheduledOrders(List<Order> unscheduledOrders) {
        this.unscheduledOrders = unscheduledOrders;
    }

    public List<FixedCapacityCargoFlight> getAbsoluteFlights() {
        return absoluteFlights;
    }

    public void setAbsoluteFlights(List<FixedCapacityCargoFlight> absoluteFlights) {
        this.absoluteFlights = absoluteFlights;
    }

    public List<SimpleOrder> getAbsoluteOrders() {
        return absoluteOrders;
    }

    public void setAbsoluteOrders(List<SimpleOrder> absoluteOrders) {
        this.absoluteOrders = absoluteOrders;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public SimpleOrderParser getSimpleOrderParser() {
        return simpleOrderParser;
    }
}

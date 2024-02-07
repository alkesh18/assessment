package org.example.model.parser.impl;

import org.example.model.airport.flight.impl.cargo.fixed.FixedCapacityCargoFlight;
import org.example.model.parser.ModelParser;

import java.util.Map;

public class FixedCapacityCargoFlightParser implements ModelParser<FixedCapacityCargoFlight> {
    @Override
    public FixedCapacityCargoFlight parseFromJson(String filepath) throws Exception {
        return null;
    }

    @Override
    public FixedCapacityCargoFlight parseFromConsoleInput(Map<String, String> input) {
        int day = 0, flightNum = 0;

        if(input == null) {
            System.out.println("Cannot parse null object");
            return null;
        }

        if(!input.containsKey("flightNum") || !input.containsKey("source") || !input.containsKey("destination") || !input.containsKey("day")) {
            System.out.println("Cannot parse null object");
            return null;
        }

        if(input.get("flightNum") != null) {
            try{
                flightNum = Integer.parseInt(input.get("flightNum"));
            } catch (Exception e) {
                System.out.println("Exception occurred when parsing");
                return null;
            }
        }

        if(input.get("day") != null) {
            try{
                day = Integer.parseInt(input.get("day"));
            } catch (Exception e) {
                System.out.println("Exception occurred when parsing");
                return null;
            }
        }

        if(input.get("source") == null || input.get("source").isBlank() || input.get("destination") == null || input.get("destination").isBlank()) {
            return null;
        }

        return new FixedCapacityCargoFlight(20, input.get("source"), input.get("destination"), day, flightNum);
    }
}

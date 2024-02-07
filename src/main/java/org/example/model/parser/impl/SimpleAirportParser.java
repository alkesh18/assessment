package org.example.model.parser.impl;

import org.example.model.airport.impl.SimpleAirport;
import org.example.model.parser.ModelParser;

import java.util.Map;

public class SimpleAirportParser implements ModelParser<SimpleAirport> {
    @Override
    public SimpleAirport parseFromJson(String filepath) throws Exception {
        return null;
    }

    @Override
    public SimpleAirport parseFromConsoleInput(Map<String, String> input) throws Exception {
        return null;
    }
}

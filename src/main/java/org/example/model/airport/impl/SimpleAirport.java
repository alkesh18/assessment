package org.example.model.airport.impl;

import org.example.model.airport.Airport;

public class SimpleAirport implements Airport {
    private String name;
    private String code;

    public SimpleAirport(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public SimpleAirport(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}

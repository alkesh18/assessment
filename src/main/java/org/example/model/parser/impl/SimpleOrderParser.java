package org.example.model.parser.impl;

import org.example.model.order.impl.SimpleOrder;
import org.example.model.parser.ModelParser;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class SimpleOrderParser implements ModelParser<List<SimpleOrder>> {
    @Override
    public List<SimpleOrder> parseFromJson(String filepath) throws Exception {
        JSONObject object;
        List<SimpleOrder> unsortedOrders = new ArrayList<>();
        List<SimpleOrder> sortedOrders = new ArrayList<>();


        try {
            object = new JSONObject(new String(Files.readAllBytes(Paths.get(filepath))));

            for(String order : object.keySet()) {
                unsortedOrders.add(new SimpleOrder(order, object.getJSONObject(order).getString("destination")));
            }

        } catch (Exception e) {
            System.out.println("Error when trying to parse json");
        }

        // JSONObject is a Map and unordered, need to order for processing, therefore sort the result before returning
        sortedOrders = unsortedOrders.stream().sorted((o1, o2)-> o1.getOrderIdentifier().
                        compareTo(o2.getOrderIdentifier())).collect(Collectors.toList());

        return sortedOrders;
    }

    @Override
    public List<SimpleOrder> parseFromConsoleInput(Map<String, String> input) throws Exception {
       return null;
    }

}

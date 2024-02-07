package org.example;

import org.example.model.airport.flight.impl.cargo.CargoFlight;
import org.example.model.parser.impl.FixedCapacityCargoFlightParser;
import org.example.service.impl.OrderSchedulingService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        OrderSchedulingService orderSchedulingService = new OrderSchedulingService();

        Scanner scanner = new Scanner(System.in);
        int select = 0;

        while(select != 4) {
            printMenu(orderSchedulingService);
            String selection = scanner.nextLine();

            try {
                select = Integer.parseInt(selection);
            } catch (Exception e) {
                System.out.println("You did not enter a valid number: ");
            }

            switch (select) {
                case 1:
                    System.out.println(orderSchedulingService.getFlightSchedule());
                    break;
                case 2:
                    System.out.println("Please enter the following information: ");
                    System.out.println("Flight Number: ");
                    String flightNum = scanner.nextLine();
                    System.out.println("Departure Airport Code: ");
                    String source = scanner.nextLine();
                    System.out.println("Arrival Airport Code: ");
                    String destination = scanner.nextLine();
                    System.out.println("Day of Flight (ex. 1, 2, 3...): ");
                    String day = scanner.nextLine();

                    Map<String, String> input = new HashMap<>();
                    input.put("flightNum", flightNum);
                    input.put("source", source);
                    input.put("destination", destination);
                    input.put("day", day);
                    FixedCapacityCargoFlightParser parser = new FixedCapacityCargoFlightParser();
                    CargoFlight flight = parser.parseFromConsoleInput(input);
                    orderSchedulingService.addCargoFlight(flight);
                    System.out.println("Flight added");
                    break;
                case 3:
                    orderSchedulingService.createItinerary();
                    System.out.println(orderSchedulingService.getOrderSchedule());
                    break;
                case 4:
                    System.out.println("Exiting");
                    break;
                default:
                    printMenu(orderSchedulingService);

            }
        }
    }

    public static void printMenu(OrderSchedulingService orderSchedulingService) {
        System.out.println("Welcome to the SpeedyAir.ly application.");
        System.out.println("Loading the existing flight schedule...");
        System.out.println(orderSchedulingService.getFlightSchedule());
        System.out.println("Please select (the number) of your options:");
        System.out.println("1 Print Flight schedule");
        System.out.println("2 Load a flight into schedule");
        System.out.println("3 Print Order itinerary");
        System.out.println("4 Exit");
    }




}
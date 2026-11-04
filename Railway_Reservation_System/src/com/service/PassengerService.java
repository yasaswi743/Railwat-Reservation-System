
package com.service;

import java.util.ArrayList;
import com.model.Passenger;

public class PassengerService {

    private ArrayList<Passenger> passengers = new ArrayList<>();
    private int nextPassengerId = 1;

    public Passenger addPassenger(String name, int age, String gender) {
        Passenger passenger = new Passenger(
                nextPassengerId++, name, age, gender);

        passengers.add(passenger);

        System.out.println("Passenger registered successfully!");
        System.out.println("Passenger ID: " + passenger.getPassengerId());

        return passenger;
    }

    public Passenger findPassengerById(int passengerId) {
        for (Passenger passenger : passengers) {
            if (passenger.getPassengerId() == passengerId) {
                return passenger;
            }
        }

        return null;
    }

    public void displayAllPassengers() {
        if (passengers.isEmpty()) {
            System.out.println("No passengers registered.");
            return;
        }

        for (Passenger passenger : passengers) {
            passenger.displayPassengerDetails();
            System.out.println("------------------------------");
        }
    }
}

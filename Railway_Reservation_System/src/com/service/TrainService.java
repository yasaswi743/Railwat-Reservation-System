
package com.service;

import java.util.ArrayList;
import com.model.Train;

public class TrainService {

    private ArrayList<Train> trains = new ArrayList<>();

    public TrainService() {
        trains.add(new Train(101, "Vande Bharat",
                "Visakhapatnam", "Vijayawada",
                "06:00 AM", "10:00 AM", 50, 750.0));

        trains.add(new Train(102, "Janmabhoomi Express",
                "Visakhapatnam", "Vijayawada",
                "07:00 AM", "12:00 PM", 75, 350.0));

        trains.add(new Train(103, "Godavari Express",
                "Vijayawada", "Rajahmundry",
                "09:00 AM", "11:30 AM", 80, 250.0));
    }

    public void displayAllTrains() {
        if (trains.isEmpty()) {
            System.out.println("No trains available.");
            return;
        }

        for (Train train : trains) {
            train.displayTrainDetails();
        }
    }

    public void searchTrains(String source, String destination) {
        boolean found = false;

        for (Train train : trains) {
            if (train.getSource().equalsIgnoreCase(source.trim())
                    && train.getDestination()
                            .equalsIgnoreCase(destination.trim())) {
                train.displayTrainDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No trains found for this route.");
        }
    }

    public Train findTrainByNumber(int trainNumber) {
        for (Train train : trains) {
            if (train.getTrainNumber() == trainNumber) {
                return train;
            }
        }
        return null;
    }

    // Admin: add a new train
    public boolean addTrain(Train train) {
        if (train == null) {
            System.out.println("Invalid train details.");
            return false;
        }

        if (findTrainByNumber(train.getTrainNumber()) != null) {
            System.out.println("Train number already exists.");
            return false;
        }

        if (train.getTotalSeats() <= 0 || train.getFare() < 0) {
            System.out.println("Invalid seats or fare.");
            return false;
        }

        trains.add(train);
        System.out.println("Train added successfully.");
        return true;
    }
}

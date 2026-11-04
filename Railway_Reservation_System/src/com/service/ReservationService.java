
package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.model.Booking;
import com.model.Passenger;
import com.model.Train;
import com.exception.InvalidBookingException;

public class ReservationService {

    private ArrayList<Booking> bookings = new ArrayList<>();

    // Each train has its own waiting queue
    private Map<Integer, Queue<Booking>> waitingLists =
            new HashMap<>();

    private int nextPnr = 100001;

    private FareCalculator fareCalculator = new FareCalculator();

    // Get or create a waiting queue for a train
    private Queue<Booking> getWaitingQueue(int trainNumber) {
        return waitingLists.computeIfAbsent(
                trainNumber, key -> new LinkedList<>());
    }

    // Book a ticket or add it to the waiting list
    public Booking bookTicket(Passenger passenger, Train train,
                              int numberOfSeats) {

        if (passenger == null || train == null) {
            throw new InvalidBookingException(
                    "Invalid passenger or train.");
        }

        if (numberOfSeats <= 0) {
            throw new InvalidBookingException(
                    "Number of seats must be positive.");
        }

        double totalFare =
                fareCalculator.calculateFare(train, numberOfSeats);

        String status;

        if (train.getTotalSeats() >= numberOfSeats) {
            status = "CONFIRMED";

            train.setTotalSeats(
                    train.getTotalSeats() - numberOfSeats);
        } else {
            status = "WAITING";
        }

        Booking booking = new Booking(
                nextPnr++,
                passenger,
                train,
                numberOfSeats,
                totalFare,
                status
        );

        bookings.add(booking);

        if ("WAITING".equals(status)) {
            getWaitingQueue(train.getTrainNumber()).offer(booking);

            System.out.println("No sufficient seats available.");
            System.out.println("Added to waiting list.");
            System.out.println("Waiting-list PNR: "
                    + booking.getPnr());
        } else {
            System.out.println("Ticket booked successfully!");
            booking.displayBookingDetails();
        }

        return booking;
    }

    // Find a booking using PNR
    public Booking findBookingByPnr(int pnr) {
        for (Booking booking : bookings) {
            if (booking.getPnr() == pnr) {
                return booking;
            }
        }

        return null;
    }

    // Display all bookings
    public void displayAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking booking : bookings) {
            booking.displayBookingDetails();
        }
    }

    // Display waiting-list bookings for all trains
    public void displayWaitingList() {
        boolean found = false;

        System.out.println("\n===== WAITING LIST =====");

        for (Map.Entry<Integer, Queue<Booking>> entry
                : waitingLists.entrySet()) {

            for (Booking booking : entry.getValue()) {
                found = true;

                System.out.println("PNR: " + booking.getPnr());
                System.out.println("Passenger: "
                        + booking.getPassenger().getName());
                System.out.println("Train Number: "
                        + booking.getTrain().getTrainNumber());
                System.out.println("Train: "
                        + booking.getTrain().getTrainName());
                System.out.println("Seats requested: "
                        + booking.getNumberOfSeats());
                System.out.println("Status: "
                        + booking.getBookingStatus());
                System.out.println("------------------------");
            }
        }

        if (!found) {
            System.out.println("Waiting list is empty.");
        }
    }

    // Cancel a ticket
    public void cancelTicket(int pnr) {
        Booking booking = findBookingByPnr(pnr);

        if (booking == null) {
            System.out.println("Invalid PNR number.");
            return;
        }

        String status = booking.getBookingStatus();

        if ("CANCELLED".equals(status)) {
            System.out.println("This ticket is already cancelled.");
            return;
        }

        Train train = booking.getTrain();

        if ("WAITING".equals(status)) {
            getWaitingQueue(train.getTrainNumber()).remove(booking);
            booking.setBookingStatus("CANCELLED");

            System.out.println("Waiting-list booking cancelled.");
            System.out.println("PNR: " + pnr);
            return;
        }

        if ("CONFIRMED".equals(status)) {
            train.setTotalSeats(
                    train.getTotalSeats()
                    + booking.getNumberOfSeats());

            booking.setBookingStatus("CANCELLED");

            System.out.println("Ticket cancelled successfully.");
            System.out.println("PNR: " + pnr);
            System.out.println("Seats released: "
                    + booking.getNumberOfSeats());

            promoteWaitingList(train);
            return;
        }

        System.out.println("Invalid booking status.");
    }

    // Promote waiting bookings in FIFO order for this train
    private void promoteWaitingList(Train train) {
        Queue<Booking> queue =
                getWaitingQueue(train.getTrainNumber());

        while (!queue.isEmpty()) {
            Booking nextBooking = queue.peek();

            // Do not skip the first waiting passenger
            if (train.getTotalSeats()
                    < nextBooking.getNumberOfSeats()) {
                break;
            }

            queue.poll();

            train.setTotalSeats(
                    train.getTotalSeats()
                    - nextBooking.getNumberOfSeats());

            nextBooking.setBookingStatus("CONFIRMED");

            System.out.println("\nWaiting-list booking confirmed!");
            System.out.println("PNR: " + nextBooking.getPnr());
            System.out.println("Passenger: "
                    + nextBooking.getPassenger().getName());

            nextBooking.displayBookingDetails();
        }
    }

    // Display booking history for a passenger
    public void displayBookingsByPassengerId(int passengerId) {
        boolean found = false;

        for (Booking booking : bookings) {
            if (booking.getPassenger().getPassengerId()
                    == passengerId) {

                booking.displayBookingDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "No bookings found for passenger ID: "
                    + passengerId);
        }
    }
}

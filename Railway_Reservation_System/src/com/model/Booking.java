
package com.model;

public class Booking {

    private int pnr;
    private Passenger passenger;
    private Train train;
    private int numberOfSeats;
    private double totalFare;
    private String bookingStatus;

    // Constructor
    public Booking(int pnr, Passenger passenger, Train train,
                   int numberOfSeats, double totalFare,
                   String bookingStatus) {
        this.pnr = pnr;
        this.passenger = passenger;
        this.train = train;
        this.numberOfSeats = numberOfSeats;
        this.totalFare = totalFare;
        this.bookingStatus = bookingStatus;
    }

    // Getters
    public int getPnr() {
        return pnr;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Train getTrain() {
        return train;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    // Update booking status
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    // Display ticket details
    public void displayBookingDetails() {
        System.out.println("\n===== TICKET DETAILS =====");
        System.out.println("PNR Number: " + pnr);

        System.out.println("\nPassenger Details:");
        passenger.displayPassengerDetails();

        System.out.println("\nTrain Details:");
        System.out.println("Train Number: " + train.getTrainNumber());
        System.out.println("Train Name: " + train.getTrainName());
        System.out.println("Source: " + train.getSource());
        System.out.println("Destination: " + train.getDestination());
        System.out.println("Departure: " + train.getDepartureTime());

        System.out.println("\nNumber of Seats: " + numberOfSeats);
        System.out.println("Total Fare: Rs. " + totalFare);
        System.out.println("Booking Status: " + bookingStatus);
        System.out.println("==========================");
    }
}

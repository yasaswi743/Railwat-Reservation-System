//RAILWAY RESERVATION SYSTEM//
package com.main;

import java.util.Scanner;

import com.model.Booking;
import com.model.Passenger;
import com.model.Train;
import com.service.AdminService;
import com.service.PassengerService;
import com.service.ReservationService;
import com.service.TrainService;
import com.exception.InvalidBookingException;

public class RailwayReservationApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        TrainService trainService = new TrainService();
        PassengerService passengerService = new PassengerService();
        ReservationService reservationService = new ReservationService();
        AdminService adminService = new AdminService();

        boolean isAdminLoggedIn = false;
        int choice = 0;

        do {
            System.out.println("\n===== RAILWAY RESERVATION SYSTEM =====");
            System.out.println("1. Display All Trains");
            System.out.println("2. Search Trains by Route");
            System.out.println("3. Register Passenger");
            System.out.println("4. Display All Passengers");
            System.out.println("5. Book Ticket");
            System.out.println("6. Search Ticket by PNR");
            System.out.println("7. Cancel Ticket");
            System.out.println("8. Display All Bookings");
            System.out.println("9. Admin Login");
            System.out.println("10. Admin - Add Train");
            System.out.println("11. Admin - Search Train by Number");
            System.out.println("12. Admin Logout");
            System.out.println("13. Display Waiting List");
            System.out.println("14. Exit");
            System.out.println("15. View Booking History by Passenger ID");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                sc.nextLine();
                continue;
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    trainService.displayAllTrains();
                    break;

                case 2:
                    System.out.print("Enter source: ");
                    String source = sc.nextLine();

                    System.out.print("Enter destination: ");
                    String destination = sc.nextLine();

                    trainService.searchTrains(source, destination);
                    break;

                case 3:
                    System.out.print("Enter passenger name: ");
                    String name = sc.nextLine().trim();

                    if (name.isEmpty()) {
                        System.out.println("Name cannot be empty.");
                        break;
                    }

                    System.out.print("Enter age: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Age must be a number.");
                        sc.nextLine();
                        break;
                    }

                    int age = sc.nextInt();
                    sc.nextLine();

                    if (age < 1 || age > 120) {
                        System.out.println("Enter a valid age.");
                        break;
                    }

                    System.out.print("Enter gender: ");
                    String gender = sc.nextLine().trim();

                    if (gender.isEmpty()) {
                        System.out.println("Gender cannot be empty.");
                        break;
                    }

                    passengerService.addPassenger(name, age, gender);
                    break;

                case 4:
                    passengerService.displayAllPassengers();
                    break;

                case 5:
                    System.out.print("Enter Passenger ID: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid Passenger ID.");
                        sc.nextLine();
                        break;
                    }

                    int passengerId = sc.nextInt();
                    sc.nextLine();

                    Passenger passenger =
                            passengerService.findPassengerById(passengerId);

                    if (passenger == null) {
                        System.out.println("Passenger not found.");
                        break;
                    }

                    System.out.print("Enter Train Number: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid train number.");
                        sc.nextLine();
                        break;
                    }

                    int trainNumber = sc.nextInt();
                    sc.nextLine();

                    Train train =
                            trainService.findTrainByNumber(trainNumber);

                    if (train == null) {
                        System.out.println("Train not found.");
                        break;
                    }

                    System.out.print("Enter number of seats: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Seats must be a number.");
                        sc.nextLine();
                        break;
                    }

                    int seats = sc.nextInt();
                    sc.nextLine();

                    try {
                        reservationService.bookTicket(
                                passenger, train, seats);
                    } catch (InvalidBookingException e) {
                        System.out.println(
                                "Booking failed: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println(
                                "Booking failed: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.print("Enter PNR: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid PNR.");
                        sc.nextLine();
                        break;
                    }

                    int searchPnr = sc.nextInt();
                    sc.nextLine();

                    Booking foundBooking =
                            reservationService.findBookingByPnr(searchPnr);

                    if (foundBooking != null) {
                        foundBooking.displayBookingDetails();
                    } else {
                        System.out.println("Booking not found.");
                    }
                    break;

                case 7:
                    System.out.print("Enter PNR to cancel: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid PNR.");
                        sc.nextLine();
                        break;
                    }

                    int cancelPnr = sc.nextInt();
                    sc.nextLine();

                    reservationService.cancelTicket(cancelPnr);
                    break;

                case 8:
                    reservationService.displayAllBookings();
                    break;

                case 9:
                    if (isAdminLoggedIn) {
                        System.out.println("Admin is already logged in.");
                        break;
                    }

                    System.out.print("Enter admin username: ");
                    String username = sc.nextLine();

                    System.out.print("Enter admin password: ");
                    String password = sc.nextLine();

                    if (adminService.login(username, password)) {
                        isAdminLoggedIn = true;
                        System.out.println("Admin login successful.");
                    } else {
                        System.out.println("Invalid admin credentials.");
                    }
                    break;

                case 10:
                    if (!isAdminLoggedIn) {
                        System.out.println("Please login as admin first.");
                        break;
                    }

                    System.out.print("Enter train number: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid train number.");
                        sc.nextLine();
                        break;
                    }

                    int newTrainNumber = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter train name: ");
                    String newTrainName = sc.nextLine().trim();

                    System.out.print("Enter source: ");
                    String newSource = sc.nextLine().trim();

                    System.out.print("Enter destination: ");
                    String newDestination = sc.nextLine().trim();

                    System.out.print("Enter departure time: ");
                    String departureTime = sc.nextLine().trim();

                    System.out.print("Enter arrival time: ");
                    String arrivalTime = sc.nextLine().trim();

                    System.out.print("Enter available seats: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Seats must be a number.");
                        sc.nextLine();
                        break;
                    }

                    int totalSeats = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter fare: ");

                    if (!sc.hasNextDouble()) {
                        System.out.println("Fare must be a number.");
                        sc.nextLine();
                        break;
                    }

                    double fare = sc.nextDouble();
                    sc.nextLine();

                    if (newTrainNumber <= 0
                            || newTrainName.isEmpty()
                            || newSource.isEmpty()
                            || newDestination.isEmpty()
                            || departureTime.isEmpty()
                            || arrivalTime.isEmpty()
                            || totalSeats <= 0
                            || Double.isNaN(fare)
                            || Double.isInfinite(fare)
                            || fare < 0) {
                        System.out.println("Invalid train details.");
                        break;
                    }

                    Train newTrain = new Train(
                            newTrainNumber,
                            newTrainName,
                            newSource,
                            newDestination,
                            departureTime,
                            arrivalTime,
                            totalSeats,
                            fare
                    );

                    trainService.addTrain(newTrain);
                    break;

                case 11:
                    if (!isAdminLoggedIn) {
                        System.out.println("Please login as admin first.");
                        break;
                    }

                    System.out.print("Enter train number: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid train number.");
                        sc.nextLine();
                        break;
                    }

                    int findTrainNumber = sc.nextInt();
                    sc.nextLine();

                    Train searchedTrain =
                            trainService.findTrainByNumber(findTrainNumber);

                    if (searchedTrain != null) {
                        searchedTrain.displayTrainDetails();
                    } else {
                        System.out.println("Train not found.");
                    }
                    break;

                case 12:
                    if (isAdminLoggedIn) {
                        isAdminLoggedIn = false;
                        System.out.println("Admin logged out.");
                    } else {
                        System.out.println("Admin is not logged in.");
                    }
                    break;

                case 13:
                    reservationService.displayWaitingList();
                    break;

                case 14:
                    System.out.println("Thank you for using the system.");
                    break;

                case 15:
                    System.out.print("Enter Passenger ID: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid Passenger ID.");
                        sc.nextLine();
                        break;
                    }

                    int historyPassengerId = sc.nextInt();
                    sc.nextLine();

                    if (passengerService.findPassengerById(
                            historyPassengerId) != null) {

                        reservationService
                                .displayBookingsByPassengerId(
                                        historyPassengerId);
                    } else {
                        System.out.println("Passenger not found.");
                    }
                    break;

                default:
                    System.out.println("Invalid menu choice.");
            }

        } while (choice != 14);

        sc.close();
    }
}


package com.service;

import com.model.Train;
import com.exception.InvalidBookingException;

public class FareCalculator {

    public double calculateFare(Train train, int numberOfSeats) {

        if (train == null) {
            throw new InvalidBookingException(
                    "Train cannot be null.");
        }

        if (numberOfSeats <= 0) {
            throw new InvalidBookingException(
                    "Number of seats must be positive.");
        }

        if (Double.isNaN(train.getFare())
                || Double.isInfinite(train.getFare())
                || train.getFare() < 0) {
            throw new InvalidBookingException(
                    "Invalid train fare.");
        }

        double totalFare = train.getFare() * numberOfSeats;

        if (Double.isInfinite(totalFare)) {
            throw new InvalidBookingException(
                    "Total fare exceeds the supported limit.");
        }

        return totalFare;
    }
}

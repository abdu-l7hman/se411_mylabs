package edu.psu.se411.lab07.service;

import java.util.List;

import edu.psu.se411.lab07.exceptions.InvalidArgumentException;
import edu.psu.se411.lab07.exceptions.MissingInformationException;
import edu.psu.se411.lab07.model.Booking;

public class BookingService {

    /**
     * Works for ANY booking type: the right computePrice() is chosen at run time.
     */
    public static double computeTotalPrice(Booking booking) throws MissingInformationException, InvalidArgumentException {
        return booking.computePrice();
    }

    /** Sum of the prices of several bookings of any type. */
    public static double computeTotalPrice(List<? extends Booking> bookings)
            throws MissingInformationException, InvalidArgumentException {
        double total = 0;
        for (Booking b : bookings) {
            total += computeTotalPrice(b);
        }
        return total;
    }
}

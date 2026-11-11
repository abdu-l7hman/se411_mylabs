package edu.psu.se411.lab07.model;

import java.time.LocalDate;

import edu.psu.se411.lab07.exceptions.InvalidArgumentException;
import edu.psu.se411.lab07.exceptions.MissingInformationException;

/**
 * Base class for every booking. Holds the information shared by all
 * booking types and declares the polymorphic price computation.
 */
public abstract class Booking {

    private final String bookingId;
    private final String customerName;
    private final LocalDate travelDate;
    private final String destinationCity;

    protected Booking(String bookingId, String customerName, LocalDate travelDate, String destinationCity) {
        if (bookingId == null || customerName == null || travelDate == null || destinationCity == null) {
            throw new IllegalArgumentException("Booking id, customer name, travel date and destination are required");
        }
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.travelDate = travelDate;
        this.destinationCity = destinationCity;
    }

    /**
     * Each booking type computes its own total price (polymorphism).
     */
    public abstract double computePrice() throws MissingInformationException, InvalidArgumentException;

    /** Short label of the booking type, used in toString(). */
    public abstract String getType();

    /**
     * Shared validation used by all subclasses (code reuse):
     * null  -> MissingInformationException
     * out of [min, max] -> InvalidArgumentException
     */
    protected void validate(Number value, String field, double min, double max)
            throws MissingInformationException, InvalidArgumentException {
        if (value == null) {
            throw new MissingInformationException(bookingId, field);
        }
        double v = value.doubleValue();
        if (v < min || v > max) {
            throw new InvalidArgumentException(bookingId, field, value, min, max);
        }
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    @Override
    public String toString() {
        return getType() + " [" + bookingId + "] " + customerName + " -> " + destinationCity + " on " + travelDate;
    }
}

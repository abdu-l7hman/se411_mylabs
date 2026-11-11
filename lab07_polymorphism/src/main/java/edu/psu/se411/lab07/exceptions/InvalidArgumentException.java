package edu.psu.se411.lab07.exceptions;

/** Thrown when a provided value is outside the allowed range. */
public class InvalidArgumentException extends BookingException {

    private static final long serialVersionUID = 1L;

    public InvalidArgumentException(String bookingId, String field, Number value, Number min, Number max) {
        super("Booking " + bookingId + ": invalid " + field + " = " + value
                + " (allowed range: " + min + " to " + max + ")");
    }
}

package edu.psu.se411.lab07.exceptions;

/**
 * Common parent of all booking exceptions, so callers can catch
 * every booking problem with a single catch block if they want.
 */
public class BookingException extends Exception {

    private static final long serialVersionUID = 1L;

    public BookingException(String message) {
        super(message);
    }
}

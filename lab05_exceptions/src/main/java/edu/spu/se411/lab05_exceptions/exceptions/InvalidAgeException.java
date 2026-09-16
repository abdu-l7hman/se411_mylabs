package edu.spu.se411.lab05_exceptions.exceptions;

/**
 * Custom exception thrown when an age value is less than 18 (not a legal adult).
 */
public class InvalidAgeException extends Exception {

    public InvalidAgeException(String message) {
        super(message);
    }
}

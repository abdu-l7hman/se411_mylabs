package edu.psu.se411.lab07.config;

/**
 * Global configuration: all rates and business limits used by the bookings.
 * Kept in one place so they can be changed without touching the booking classes.
 */
public final class GlobalConfig {

    private GlobalConfig() {
        // constants only, no instances
    }

    // Flight
    public static final double EXTRA_LUGGAGE_RATE = 15.0;      // SAR per kg
    public static final double MIN_LUGGAGE_WEIGHT = 0.0;       // kg
    public static final double MAX_LUGGAGE_WEIGHT = 40.0;      // kg

    // Train
    public static final double TRAIN_STANDARD_RATE = 0.5;      // SAR per km
    public static final double TRAIN_FIRST_CLASS_RATE = 0.9;   // SAR per km
    public static final double MIN_TRAIN_DISTANCE = 1.0;       // km
    public static final double MAX_TRAIN_DISTANCE = 2000.0;    // km

    // Car rental
    public static final int MIN_RENTAL_DAYS = 1;
    public static final int MAX_RENTAL_DAYS = 30;
}

package edu.psu.se411.lab07.model;

import edu.psu.se411.lab07.config.GlobalConfig;

/** Train seat class, each with its own rate per km. */
public enum SeatClass {
    STANDARD(GlobalConfig.TRAIN_STANDARD_RATE),
    FIRST_CLASS(GlobalConfig.TRAIN_FIRST_CLASS_RATE);

    private final double ratePerKm;

    SeatClass(double ratePerKm) {
        this.ratePerKm = ratePerKm;
    }

    public double getRatePerKm() {
        return ratePerKm;
    }
}

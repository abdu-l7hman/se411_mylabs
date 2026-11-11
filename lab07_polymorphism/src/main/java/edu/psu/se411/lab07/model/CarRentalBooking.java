package edu.psu.se411.lab07.model;

import java.time.LocalDate;

import edu.psu.se411.lab07.config.GlobalConfig;
import edu.psu.se411.lab07.exceptions.InvalidArgumentException;
import edu.psu.se411.lab07.exceptions.MissingInformationException;

public class CarRentalBooking extends Booking {

    private final double dailyRate;
    private Integer rentalDays; // entered later by the customer

    public CarRentalBooking(String bookingId, String customerName, LocalDate travelDate, String destinationCity,
            double dailyRate) {
        super(bookingId, customerName, travelDate, destinationCity);
        if (dailyRate < 0) {
            throw new IllegalArgumentException("Daily rate cannot be negative");
        }
        this.dailyRate = dailyRate;
    }

    public void setRentalDays(Integer rentalDays) throws MissingInformationException, InvalidArgumentException {
        validate(rentalDays, "rental days", GlobalConfig.MIN_RENTAL_DAYS, GlobalConfig.MAX_RENTAL_DAYS);
        this.rentalDays = rentalDays;
    }

    @Override
    public double computePrice() throws MissingInformationException, InvalidArgumentException {
        validate(rentalDays, "rental days", GlobalConfig.MIN_RENTAL_DAYS, GlobalConfig.MAX_RENTAL_DAYS);
        return dailyRate * rentalDays;
    }

    @Override
    public String getType() {
        return "Car rental";
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public Integer getRentalDays() {
        return rentalDays;
    }
}

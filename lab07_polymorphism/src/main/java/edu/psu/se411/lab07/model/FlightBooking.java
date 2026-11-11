package edu.psu.se411.lab07.model;

import java.time.LocalDate;

import edu.psu.se411.lab07.config.GlobalConfig;
import edu.psu.se411.lab07.exceptions.InvalidArgumentException;
import edu.psu.se411.lab07.exceptions.MissingInformationException;

public class FlightBooking extends Booking {

    private final double basePrice;
    private Double luggageWeight; // entered later by the customer

    public FlightBooking(String bookingId, String customerName, LocalDate travelDate, String destinationCity,
            double basePrice) {
        super(bookingId, customerName, travelDate, destinationCity);
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }
        this.basePrice = basePrice;
    }

    public void setLuggageWeight(Double luggageWeight) throws MissingInformationException, InvalidArgumentException {
        validate(luggageWeight, "luggage weight", GlobalConfig.MIN_LUGGAGE_WEIGHT, GlobalConfig.MAX_LUGGAGE_WEIGHT);
        this.luggageWeight = luggageWeight;
    }

    @Override
    public double computePrice() throws MissingInformationException, InvalidArgumentException {
        validate(luggageWeight, "luggage weight", GlobalConfig.MIN_LUGGAGE_WEIGHT, GlobalConfig.MAX_LUGGAGE_WEIGHT);
        return basePrice + luggageWeight * GlobalConfig.EXTRA_LUGGAGE_RATE;
    }

    @Override
    public String getType() {
        return "Flight";
    }

    public double getBasePrice() {
        return basePrice;
    }

    public Double getLuggageWeight() {
        return luggageWeight;
    }
}

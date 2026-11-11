package edu.psu.se411.lab07.model;

import java.time.LocalDate;

import edu.psu.se411.lab07.config.GlobalConfig;
import edu.psu.se411.lab07.exceptions.InvalidArgumentException;
import edu.psu.se411.lab07.exceptions.MissingInformationException;

public class TrainBooking extends Booking {

    private final SeatClass seatClass;
    private Double distanceKm; // entered later by the system

    public TrainBooking(String bookingId, String customerName, LocalDate travelDate, String destinationCity,
            SeatClass seatClass) {
        super(bookingId, customerName, travelDate, destinationCity);
        if (seatClass == null) {
            throw new IllegalArgumentException("Seat class is required");
        }
        this.seatClass = seatClass;
    }

    public void setDistanceKm(Double distanceKm) throws MissingInformationException, InvalidArgumentException {
        validate(distanceKm, "distance (km)", GlobalConfig.MIN_TRAIN_DISTANCE, GlobalConfig.MAX_TRAIN_DISTANCE);
        this.distanceKm = distanceKm;
    }

    @Override
    public double computePrice() throws MissingInformationException, InvalidArgumentException {
        validate(distanceKm, "distance (km)", GlobalConfig.MIN_TRAIN_DISTANCE, GlobalConfig.MAX_TRAIN_DISTANCE);
        return distanceKm * seatClass.getRatePerKm();
    }

    @Override
    public String getType() {
        return "Train (" + seatClass + ")";
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }
}

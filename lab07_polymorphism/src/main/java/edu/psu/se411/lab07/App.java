package edu.psu.se411.lab07;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab07.exceptions.BookingException;
import edu.psu.se411.lab07.model.Booking;
import edu.psu.se411.lab07.model.CarRentalBooking;
import edu.psu.se411.lab07.model.FlightBooking;
import edu.psu.se411.lab07.model.SeatClass;
import edu.psu.se411.lab07.model.TrainBooking;
import edu.psu.se411.lab07.service.BookingService;

/**
 * Build and run with the Maven goal: clean package exec:java
 */
public class App {

    static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Application is starting...");

        LocalDate date = LocalDate.of(2026, 12, 20);

        // ---------- 1. Valid bookings of every type ----------
        List<Booking> bookings = new ArrayList<>();
        try {
            FlightBooking flight = new FlightBooking("F-001", "Ahmed Ali", date, "Riyadh", 850.0);
            flight.setLuggageWeight(23.0);

            TrainBooking train1 = new TrainBooking("T-001", "Sara Omar", date, "Makkah", SeatClass.STANDARD);
            train1.setDistanceKm(450.0);

            TrainBooking train2 = new TrainBooking("T-002", "Khalid Saad", date, "Madinah", SeatClass.FIRST_CLASS);
            train2.setDistanceKm(450.0);

            CarRentalBooking car = new CarRentalBooking("C-001", "Noura Fahad", date, "Jeddah", 180.0);
            car.setRentalDays(5);

            bookings.add(flight);
            bookings.add(train1);
            bookings.add(train2);
            bookings.add(car);

            System.out.println("=== Valid bookings ===");
            for (Booking b : bookings) {
                // Polymorphism: same call, different price rule for each type
                System.out.printf("%-66s total = %10.2f SAR%n", b, BookingService.computeTotalPrice(b));
            }
            System.out.printf("Total of all bookings = %.2f SAR%n%n", BookingService.computeTotalPrice(bookings));
        } catch (BookingException e) {
            logger.error("Unexpected booking error: " + e.getMessage());
        }

        // ---------- 2. Business-rule violations ----------
        System.out.println("=== Error cases ===");

        // Flight: luggage weight never provided -> MissingInformationException
        FlightBooking noLuggage = new FlightBooking("F-002", "Omar Hassan", date, "Dammam", 600.0);
        tryCompute(noLuggage);

        // Flight: luggage weight outside 0..40 kg -> InvalidArgumentException
        try {
            FlightBooking heavy = new FlightBooking("F-003", "Lina Adel", date, "Abha", 700.0);
            heavy.setLuggageWeight(55.0);
        } catch (BookingException e) {
            report(e);
        }

        // Train: distance never provided -> MissingInformationException
        TrainBooking noDistance = new TrainBooking("T-003", "Faisal Nasser", date, "Hail", SeatClass.STANDARD);
        tryCompute(noDistance);

        // Train: distance outside 1..2000 km -> InvalidArgumentException
        try {
            TrainBooking tooFar = new TrainBooking("T-004", "Reem Saleh", date, "Tabuk", SeatClass.FIRST_CLASS);
            tooFar.setDistanceKm(2500.0);
        } catch (BookingException e) {
            report(e);
        }

        // Car rental: days never provided -> MissingInformationException
        CarRentalBooking noDays = new CarRentalBooking("C-002", "Yousef Ali", date, "Taif", 150.0);
        tryCompute(noDays);

        // Car rental: days outside 1..30 -> InvalidArgumentException
        try {
            CarRentalBooking tooLong = new CarRentalBooking("C-003", "Huda Khalid", date, "AlUla", 200.0);
            tooLong.setRentalDays(45);
        } catch (BookingException e) {
            report(e);
        }

        logger.info("Application is closing...");
    }

    private static void tryCompute(Booking booking) {
        try {
            BookingService.computeTotalPrice(booking);
        } catch (BookingException e) {
            report(e);
        }
    }

    private static void report(BookingException e) {
        System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        logger.error(e.getClass().getSimpleName() + " raised: " + e.getMessage());
    }
}

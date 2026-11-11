# Lab 07 – Polymorphism in Java

Travel agency booking system: flights, train rides and car rentals.

Run: `clean package exec:java`

## Design
- `model/Booking` (abstract): booking id, customer name, travel date, destination city,
  the abstract `computePrice()` and a shared `validate()` helper used by every subclass (code reuse).
- `FlightBooking`: base price + luggage weight × `EXTRA_LUGGAGE_RATE`, luggage 0–40 kg.
- `TrainBooking`: distance × rate of the `SeatClass` (`STANDARD` / `FIRST_CLASS`), distance 1–2000 km.
- `CarRentalBooking`: daily rate × number of days, 1–30 days.
- `config/GlobalConfig`: all rates and limits.
- `exceptions/MissingInformationException` (value never provided) and
  `exceptions/InvalidArgumentException` (value outside the allowed range), both extend `BookingException`.
- `service/BookingService.computeTotalPrice(Booking booking)` works for any booking type (polymorphism).
- `App`: tests valid bookings and all six error cases; logs start/stop and every raised exception
  to `logs/App/log4j/log.out` (SLF4J + log4j12).

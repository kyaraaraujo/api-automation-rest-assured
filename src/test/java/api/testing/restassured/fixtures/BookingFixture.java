package api.testing.restassured.fixtures;

import api.testing.restassured.domain.Booking;
import api.testing.restassured.domain.BookingDates;

public class BookingFixture {

    private BookingFixture() {
    }

    public static Booking bookingId(){
        BookingDates dates = BookingDates.builder()
                .checkIn("2022-12-25")
                .checkOut("2022-01-10")
                .build();

        return Booking.builder()
                .firstName("Ivan")
                .lastName("Motors")
                .totalPrice(200)
                .depositPaid(false)
                .bookingDates(dates)
                .additionalNeeds("Parking")
                .build();
    }
}

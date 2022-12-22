package api.testing.restassured.fixtures;

import api.testing.restassured.domain.BookingAdmin;

public abstract class BookingAdminFixture {

    private BookingAdminFixture() {
    }

    public static BookingAdmin getCredentials(){
        return BookingAdmin.builder()
                .username("admin")
                .password("password123")
                .build();
    }
}

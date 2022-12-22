package api.testing.restassured.tests;

import api.testing.restassured.domain.Booking;
import api.testing.restassured.fixtures.BookingAdminFixture;
import api.testing.restassured.fixtures.BookingFixture;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BookingTest extends BaseTest {
    private final String AUTH_END_POINT = "/auth";
    private final String BOOKING_ID_END_POINT = "/booking/{id}";
    private final String BOOKING_END_POINT = "/booking";
    private final String ID_PATH_PARAM = "id";
    private final int ID_NUMBER = 82;

    @Test
    @Order(1)
    void updatesASpecificBookingByIdWhenAuthenticated(){
        Booking fixture = BookingFixture.bookingId();

        // https://restful-booker.herokuapp.com/auth
        String token = given()
                            .body(BookingAdminFixture.getCredentials())
                        .when()
                            .post(AUTH_END_POINT)
                        .then()
                            .extract().path("token");

        // https://restful-booker.herokuapp.com/booking/:id
        Booking response = given()
                .header("Cookie", "token=" + token )
                .pathParam(ID_PATH_PARAM, ID_NUMBER)
                .body(fixture)
            .when()
                .put(BOOKING_ID_END_POINT)
            .then()
                .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().body().as(Booking.class);

        assertThat(response, equalTo(fixture));
    }

    @Test
    @Order(2)
    void getABookingById(){
        Booking fixture = BookingFixture.bookingId();

        given()
            .pathParam(ID_PATH_PARAM, ID_NUMBER)
        .when()
            .get(BOOKING_ID_END_POINT)
        .then()
            .assertThat().log().body()
                .statusCode(HttpStatus.SC_OK)
                    .body("firstname", is(fixture.getFirstName()),
                     "lastname", is(fixture.getLastName()),
                             "totalprice", is(fixture.getTotalPrice()),
                             "depositpaid", is(false),
                             "bookingdates.checkin", is(fixture.getBookingDates().getCheckIn()),
                             "bookingdates.checkout", is(fixture.getBookingDates().getCheckOut())
                            );
    }

    @Test
    @Order(3)
    void validatesABookingSchema(){
        given()
                .pathParam(ID_PATH_PARAM, ID_NUMBER)
            .when()
                .get(BOOKING_ID_END_POINT)
            .then().log().all()
                .assertThat()
                .body(matchesJsonSchema(new File("src/test/java/booking/api/test/restassured/schemas/booking-schema.json")));
//                .body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
    }

    @Test
    @Order(3)
    void findBookingsIdsByName(){
        Booking fixture = BookingFixture.bookingId();
        Map<String, String> person = new HashMap<>();
        person.put("firstname", fixture.getFirstName());
        person.put("lastname", fixture.getLastName());

        given()
            .queryParams(person)
        .when()
            .get(BOOKING_END_POINT)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body("size()", Matchers.greaterThan(0));
    }
}

package api.testing.restassured.tests;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class UserTest {

    private static final int PAGE_NUMBER = 2;

    static Response getUsersPerPage(){
        return given()
                    .param("page", PAGE_NUMBER)
                .when()
                    .get("https://reqres.in/api/users");
    }

    @Test
    void checkIfTheNumberOfUsersDisplayedAreTheSameAsUsersPerPage(){
        Response get = getUsersPerPage();
        int pageNumber = 2;
        int numberOfUsersPerPage = get
                .then()
                    .statusCode(HttpStatus.SC_OK)
                        .extract().path("per_page");

        get.then()
            .statusCode(HttpStatus.SC_OK)
            .body("page", is(pageNumber),
            "data.size()", is(numberOfUsersPerPage),
                    "data.findAll { it.avatar.startsWith('https://reqres.in/img') }.size() ", is(numberOfUsersPerPage));
    }
}




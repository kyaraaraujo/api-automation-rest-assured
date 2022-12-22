package api.testing.restassured.tests;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ApiHealthCheckTest extends BaseTest{

    @BeforeAll
    static void setupHealthCheck(){
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.TEXT)
                .build();
    }

    @Test
    void verifyApiHealthCheck(){
        given()
            .basePath("ping")
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.SC_CREATED);
    }
}

package api.testing.restassured.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class BaseTest {

    @BeforeAll
    static void setUp(){
        enableLoggingOfRequestAndResponseIfValidationFails();
        baseURI = "https://restful-booker.herokuapp.com";

        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }
}

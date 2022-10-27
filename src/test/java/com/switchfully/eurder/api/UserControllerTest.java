package com.switchfully.eurder.api;

import com.switchfully.eurder.api.dtos.CustomerDto;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

    @LocalServerPort
    private int port;

    private static String requestBody =String.format( """
            {
              "firstName": "fname",
              "lastName": "lname",
              "eMail": "string@qd.com",
              "street": "string",
              "houseNumber": "string",
              "postCode": %s,
              "city": "Hasselt",
              "phoneNumber": "04653122246",
              "password": "password"
            }
            ""","3500");
    @Test
    void addCustomer() {
        CustomerDto response= given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CustomerDto.class);
        Assertions.assertThat(response.getFirstName()).isEqualTo("fname");
    }
}
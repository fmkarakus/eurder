package com.switchfully.eurder.api;

import com.switchfully.eurder.api.dtos.CustomerDto;
import com.switchfully.eurder.repositories.ItemRepository;
import com.switchfully.eurder.repositories.UserRepository;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @LocalServerPort
    private int port;

    @Test
    void addCustomer() {
        String requestBody = String.format("""
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
            """, "3500");
        CustomerDto response = given()
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

    @Test
    void viewAllCustomers() {
        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("admin@eurder.com", "password")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    @Test
    void addOrder() {
        String aUserId = userRepository.getPersonbyEmail("customer@test.be").get().getId();
        String itemId1 = itemRepository.getItemMap().values().stream().filter(item -> item.getName().equals("item1")).findFirst().get().getId();
        String itemId2 = itemRepository.getItemMap().values().stream().filter(item -> item.getName().equals("item2")).findFirst().get().getId();
        String requestBody = String.format("""
                [
                  {
                    "itemId": "%s",
                    "amount": 2
                  },
                    {
                    "itemId": "%s",
                    "amount": 2
                  }
                ]
                """, itemId1, itemId2);
        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("customer@test.be", "password")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/customers/" + aUserId + "/order")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }
}
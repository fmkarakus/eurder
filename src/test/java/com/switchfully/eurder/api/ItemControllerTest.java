package com.switchfully.eurder.api;

import com.switchfully.eurder.repositories.ItemRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
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
class ItemControllerTest {
    @Autowired
    private ItemRepository itemRepository;
    private static String requestBody = """
            {
              "name": "string",
              "description": "string",
              "price": 5.2,
              "amount": 7
            }
            """;
    @LocalServerPort
    private int port;

    @Test
    void addItem() {
        given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .auth()
                .preemptive()
                .basic("admin@eurder.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .body(requestBody)
                .when()
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    @Test
    void updateItem() {
        String itemId1 = itemRepository.getItemMap().values().stream().filter(item -> item.getName().equals("item1")).findFirst().get().getId();
        given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .auth()
                .preemptive()
                .basic("admin@eurder.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .body(requestBody)
                .when()
                .patch("/items/"+itemId1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    @Test
    void addItem_whenItemNameIsBlank_ThenThrowsException() {
        String requestBody = """
            {
              "name": "",
              "description": "string",
              "price": 5.2,
              "amount": 7
            }
            """;
        given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .auth()
                .preemptive()
                .basic("admin@eurder.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .body(requestBody)
                .when()
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }

    @Test
    void viewAllItems() {
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
                .get("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }
}
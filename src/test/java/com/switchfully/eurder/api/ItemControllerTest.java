package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.item.StockStatus;
import com.switchfully.eurder.service.item.dto.CreateItemDto;
import com.switchfully.eurder.service.item.dto.ItemDto;
import com.switchfully.eurder.service.item.dto.StockDto;
import com.switchfully.eurder.service.item.dto.UpdateItemDto;
import com.switchfully.eurder.service.item.ItemMapper;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.repositories.ItemRepository;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class ItemControllerTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemMapper itemMapper;
    private static CreateItemDto requestBody = new CreateItemDto(
            "testItem",
            "string",
            5.2,
            7
    );

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
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void updateItem() {
        Item item = itemRepository.save(itemMapper.mapToItem(requestBody));
        UpdateItemDto updateItem = new UpdateItemDto("updated", "", 4, 5);
        ItemDto result = given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .auth()
                .preemptive()
                .basic("admin@eurder.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .body(updateItem)
                .when()
                .patch("/items/" + item.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ItemDto.class);
        Assertions.assertThat(result.getName()).isEqualTo(updateItem.getName());
        Assertions.assertThat(result.getPrice()).isEqualTo(updateItem.getPrice());
    }

    @Test
    void addItem_whenItemNameIsBlank_ThenThrowsException() {
        CreateItemDto requestBody = new CreateItemDto("", "string", 5.2, 7);
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
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    void viewAllItems() {
        itemRepository.save(itemMapper.mapToItem(requestBody));
        ItemDto[] result = given()
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
                .statusCode(HttpStatus.OK.value()).
                extract().as(ItemDto[].class);
        Assertions.assertThat(result.length).isEqualTo(1);
    }
    @Test
    void viewItemOverview() {
        itemRepository.save(itemMapper.mapToItem(requestBody));
        StockDto[] result = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("admin@eurder.com", "password")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/items/stock")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value()).
                extract().as(StockDto[].class);
        Assertions.assertThat(result[0].stockStatus()).isEqualTo(StockStatus.STOCK_MEDIUM);
    }

    @Test
    void viewItemOverview_whenFiltered() {
        itemRepository.save(itemMapper.mapToItem(requestBody));
        StockDto[] result = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("admin@eurder.com", "password")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .and()
                .when()
                .param("status","STOCK_MEDIUM")
                .get("/items/stock")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value()).
                extract().as(StockDto[].class);
        Assertions.assertThat(result[0].stockStatus()).isEqualTo(StockStatus.STOCK_MEDIUM);
    }

}
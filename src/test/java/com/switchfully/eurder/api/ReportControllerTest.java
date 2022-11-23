package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.users.Person;
import com.switchfully.eurder.repositories.ItemRepository;
import com.switchfully.eurder.repositories.PersonRepository;
import com.switchfully.eurder.service.user.UserMapper;
import com.switchfully.eurder.service.user.UserService;
import com.switchfully.eurder.service.user.orderDto.CreateItemGroupDto;
import com.switchfully.eurder.service.user.orderDto.ShowOrderDto;
import com.switchfully.eurder.service.user.orderDto.TodaysOrderDto;
import com.switchfully.eurder.service.user.userDto.CreateCustomerDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class ReportControllerTest {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserService userService;
    @LocalServerPort
    private int port;

    @Test
    void getOrdersOfToday() {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("fname", "lname", "customer@test.be", "string", "65", "3500", "Hasselt", "04653122246", "password");
        Person person = personRepository.save(userMapper.mapToUser(createCustomerDto));
        long itemId1 = (itemRepository.save(new Item("item1", "desc", 2.5, 5))).getId();
        long itemId2 = (itemRepository.save(new Item("item2", "desc", 2.5, 5))).getId();
        List<CreateItemGroupDto> orders = new ArrayList<>(Arrays.asList(new CreateItemGroupDto(itemId1, 2), new CreateItemGroupDto(itemId2, 2)));
        userService.addOrder(person.getId(), orders);

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
                .get("/reports/todaysOrders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

    }
}
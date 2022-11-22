package com.switchfully.eurder.api;

import com.switchfully.eurder.api.dtos.CreateCustomerDto;
import com.switchfully.eurder.api.dtos.CustomerDto;
import com.switchfully.eurder.api.mappers.UserMapper;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.users.Person;
import com.switchfully.eurder.repositories.ItemRepository;
import com.switchfully.eurder.repositories.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class PersonControllerTest {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ItemRepository itemRepository;
    @LocalServerPort
    private int port;

    @Test
    void addCustomer() {
        CreateCustomerDto createCustomerDto=new CreateCustomerDto("fname","lname","string@qd.com","string","65","3500","Hasselt","04653122246","password");
        CustomerDto response = given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .and()
                .body(createCustomerDto)
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
    void viewOneCustomer() {
        CreateCustomerDto createCustomerDto=new CreateCustomerDto("fname","lname","string@qd.com","string","65","3500","Hasselt","04653122246","password");
        Person person= personRepository.save(userMapper.mapToUser(createCustomerDto));
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
                .get("/customers/"+person.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }
    @Test
    void viewOneCustomer_WrongCredentialsThrowsError() {
        CreateCustomerDto createCustomerDto=new CreateCustomerDto("fname","lname","customer@test.be","string","65","3500","Hasselt","04653122246","password");
        Person person= personRepository.save(userMapper.mapToUser(createCustomerDto));
        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("wrong@eurder.com", "wrong")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/customers/"+person.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }
    @Test
    void viewOneCustomer_whenByCustomer_thenThrowsException() {
        CreateCustomerDto createCustomerDto=new CreateCustomerDto("fname","lname","customer@test.be","string","65","3500","Hasselt","04653122246","password");
        Person person= personRepository.save(userMapper.mapToUser(createCustomerDto));
        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("customer@test.be", "password")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/customers/"+person.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }

    @Test
    void addOrder() {
        CreateCustomerDto createCustomerDto=new CreateCustomerDto("fname","lname","customer@test.be","string","65","3500","Hasselt","04653122246","password");
        Person person= personRepository.save(userMapper.mapToUser(createCustomerDto));
        long itemId1 = (itemRepository.save(new Item("item1","desc",2.5,5))).getId();
        long itemId2 = (itemRepository.save(new Item("item2","desc",2.5,5))).getId();

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
                .post("/customers/" + person.getId() + "/order")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    @Test
    void addOrder_whenUserDoesNotExist_ThrowsException() {
        String aUserId = "wrongid";
        long itemId1 = (itemRepository.save(new Item("item1","desc",2.5,5))).getId();
        long itemId2 = (itemRepository.save(new Item("item2","desc",2.5,5))).getId();
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
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void addOrder_whenItemDoesNotExist_ThrowsException() {
        CreateCustomerDto createCustomerDto=new CreateCustomerDto("fname","lname","customer@test.be","string","65","3500","Hasselt","04653122246","password");
        Person person= personRepository.save(userMapper.mapToUser(createCustomerDto));
        long itemId1 = 9999;
        long itemId2 = (itemRepository.save(new Item("item2","desc",2.5,5))).getId();
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
                .post("/customers/" + person.getId() + "/order")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }

    @Test
    void getCustomerOrders() {
        CreateCustomerDto createCustomerDto=new CreateCustomerDto("fname","lname","customer@test.be","string","65","3500","Hasselt","04653122246","password");
        Person person= personRepository.save(userMapper.mapToUser(createCustomerDto));

        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("customer@test.be", "password")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/customers/" + person.getId() + "/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }
}
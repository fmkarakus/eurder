package com.switchfully.eurder.api;

import com.switchfully.eurder.service.user.UserMapper;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.service.user.UserService;
import com.switchfully.eurder.service.user.orderDto.CreateItemGroupDto;
import com.switchfully.eurder.service.user.orderDto.ShowOrderDto;
import com.switchfully.eurder.domain.users.Person;
import com.switchfully.eurder.service.user.userDto.CreateCustomerDto;
import com.switchfully.eurder.service.user.userDto.CustomerDto;
import com.switchfully.eurder.service.user.userDto.ShowUserDto;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @Autowired
    private UserService userService;
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
        CreateCustomerDto createCustomerDto=new CreateCustomerDto("fname","lname","string@qd.com","string","65","3500","Hasselt","04653122246","password");
        Person person= personRepository.save(userMapper.mapToUser(createCustomerDto));
        CustomerDto[] customers=  given()
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
                .extract()
                .as(CustomerDto[].class);
        Assertions.assertThat(customers.length).isEqualTo(2);
    }

    @Test
    void viewOneCustomer() {
        CreateCustomerDto createCustomerDto=new CreateCustomerDto("fname","lname","string@qd.com","string","65","3500","Hasselt","04653122246","password");
        Person person= personRepository.save(userMapper.mapToUser(createCustomerDto));
        ShowUserDto customer=given()
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
                .extract()
                .as(ShowUserDto.class);
        Assertions.assertThat(customer.getFirstName()).isEqualTo("fname");
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
                .body("message", equalTo("Wrong credentials!"));
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
                .body("message", equalTo("You do not have the authorization for this feature"));
    }

    @Test
    void addOrder() {
        CreateCustomerDto createCustomerDto=new CreateCustomerDto("fname","lname","customer@test.be","string","65","3500","Hasselt","04653122246","password");
        Person person= personRepository.save(userMapper.mapToUser(createCustomerDto));
        long itemId1 = (itemRepository.save(new Item("item1","desc",2.5,5))).getId();
        long itemId2 = (itemRepository.save(new Item("item2","desc",2.5,5))).getId();
        CreateItemGroupDto[] requestBody={new CreateItemGroupDto(itemId1,2),new CreateItemGroupDto(itemId2,2)};
        ShowOrderDto result=
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
                .extract()
                .as(ShowOrderDto.class);
        Assertions.assertThat(result.getTotalPrice()).isEqualTo(10);
        Assertions.assertThat(result.getOrders().size()).isEqualTo(2);
    }

    @Test
    void addOrder_whenUserDoesNotExist_ThrowsException() {
        CreateCustomerDto createCustomerDto=new CreateCustomerDto("fname","lname","customer@test.be","string","65","3500","Hasselt","04653122246","password");
        Person person= personRepository.save(userMapper.mapToUser(createCustomerDto));
        long aUserId = 5555;
        long itemId1 = (itemRepository.save(new Item("item1","desc",2.5,5))).getId();
        long itemId2 = (itemRepository.save(new Item("item2","desc",2.5,5))).getId();
        CreateItemGroupDto[] requestBody={new CreateItemGroupDto(itemId1,2),new CreateItemGroupDto(itemId2,2)};
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
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo("There is no customer with the id 5555."));

    }

    @Test
    void addOrder_whenItemDoesNotExist_ThrowsException() {
        CreateCustomerDto createCustomerDto=new CreateCustomerDto("fname","lname","customer@test.be","string","65","3500","Hasselt","04653122246","password");
        Person person= personRepository.save(userMapper.mapToUser(createCustomerDto));
        long itemId1 = 9999;
        long itemId2 = (itemRepository.save(new Item("item2","desc",2.5,5))).getId();
        CreateItemGroupDto[] requestBody={new CreateItemGroupDto(itemId1,2),new CreateItemGroupDto(itemId2,2)};
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
                .body("message", equalTo("Item with the id 9999 does not exist."));
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

    @Test
    void reOrder() {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("fname", "lname", "customer@test.be", "string", "65", "3500", "Hasselt", "04653122246", "password");
        Person person = personRepository.save(userMapper.mapToUser(createCustomerDto));
        long itemId1 = (itemRepository.save(new Item("item1", "desc", 2.5, 5))).getId();
        long itemId2 = (itemRepository.save(new Item("item2", "desc", 2.5, 5))).getId();
        List<CreateItemGroupDto> orders = new ArrayList<>(Arrays.asList(new CreateItemGroupDto(itemId1, 2), new CreateItemGroupDto(itemId2, 2)));
        ShowOrderDto orderDto=userService.addOrder(person.getId(), orders);
        ShowOrderDto result=
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
                        .post("/customers/" + person.getId() + "/orders/"+orderDto.getOrderId())
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(ShowOrderDto.class);
        Assertions.assertThat(result.getOrderId()).isEqualTo(orderDto.getOrderId()+1);


    }
}
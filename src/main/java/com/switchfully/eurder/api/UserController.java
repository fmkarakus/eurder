package com.switchfully.eurder.api;

import com.switchfully.eurder.api.dtos.*;
import com.switchfully.eurder.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("customers")
public class UserController {
   private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@Valid @RequestBody CreateCustomerDto newCustomer){
        return userService.addCustomer(newCustomer);
    }

    @PostMapping(path="{userId}/order",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ShowOrderDto createOrder(@PathVariable String userId, @RequestHeader String authorization, @Valid @RequestBody CreateItemGroupDto[] newOrders){
        return userService.addOrder(userId,authorization,newOrders);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAllUsers(@RequestHeader String authorization) {
        return userService.getAllUsers(authorization);
    }

    @GetMapping(path="{customerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ShowUserDto getCustomer(@RequestHeader String authorization, @PathVariable String customerId) {
        return userService.getCustomer(authorization,customerId);
    }

    @GetMapping(path="{customerId}/orders",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ShowAllOrdersDto getCustomerOrders(@RequestHeader String authorization, @PathVariable String customerId) {
        return userService.getCustomerOrders(authorization,customerId);
    }
}

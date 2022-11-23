package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.users.Feature;
import com.switchfully.eurder.service.security.SecurityService;
import com.switchfully.eurder.service.user.orderDto.CreateItemGroupDto;
import com.switchfully.eurder.service.user.orderDto.ShowAllOrdersDto;
import com.switchfully.eurder.service.user.orderDto.ShowOrderDto;
import com.switchfully.eurder.service.user.userDto.CreateCustomerDto;
import com.switchfully.eurder.service.user.userDto.CustomerDto;
import com.switchfully.eurder.service.user.userDto.ShowUserDto;
import com.switchfully.eurder.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("customers")
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@Valid @RequestBody CreateCustomerDto newCustomer) {
        return userService.addCustomer(newCustomer);
    }

    @PostMapping(path = "{userId}/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ShowOrderDto createOrder(@PathVariable long userId, @RequestHeader String authorization, @Valid @RequestBody List<CreateItemGroupDto> newOrders) {
        securityService.validateAuthorization(authorization, Feature.ORDER);
        return userService.addOrder(userId, newOrders);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAllUsers(@RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Feature.VIEW_USERS);
        return userService.getAllUsers();
    }

    @GetMapping(path = "{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ShowUserDto getCustomer(@RequestHeader String authorization, @PathVariable long customerId) {
        securityService.validateAuthorization(authorization, Feature.VIEW_USERS);
        return userService.getCustomer(customerId);
    }

    @GetMapping(path = "{customerId}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ShowAllOrdersDto getCustomerOrders(@RequestHeader String authorization, @PathVariable long customerId) {
        securityService.validateAuthorization(authorization, Feature.ORDER);
        return userService.getCustomerOrders(customerId);
    }
    @PostMapping(path = "{customerId}/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ShowOrderDto reOrder(@RequestHeader String authorization, @PathVariable long customerId,@PathVariable long orderId) {
        securityService.validateAuthorization(authorization, Feature.ORDER);
        return userService.reorder(customerId,orderId);
    }
}

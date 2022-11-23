package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.users.Feature;
import com.switchfully.eurder.service.security.SecurityService;
import com.switchfully.eurder.service.user.UserService;
import com.switchfully.eurder.service.user.orderDto.OrderOfTheDayDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reports")
public class ReportController {

    private final UserService userService;
    private final SecurityService securityService;

    public ReportController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping(path = "todaysOrders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<OrderOfTheDayDto> getOrdersOfToday(@RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Feature.VIEW_TODAYS_ORDERS);
        return userService.getTodaysOrders();
    }
}

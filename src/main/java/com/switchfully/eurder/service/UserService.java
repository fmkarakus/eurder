package com.switchfully.eurder.service;

import com.switchfully.eurder.api.OrderMapper;
import com.switchfully.eurder.api.UserMapper;
import com.switchfully.eurder.api.dtos.CreateCustomerDto;
import com.switchfully.eurder.api.dtos.CreateItemGroupDto;
import com.switchfully.eurder.api.dtos.CustomerDto;
import com.switchfully.eurder.api.dtos.ShowOrderDto;
import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.domain.users.Feature;
import com.switchfully.eurder.domain.users.User;
import com.switchfully.eurder.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

    public UserService(SecurityService securityService, UserRepository userRepository, UserMapper userMapper, OrderMapper orderMapper) {
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
    }

    public CustomerDto addCustomer(CreateCustomerDto newCustomer) {
        User newUser = userRepository.addCustomer(userMapper.mapToUser(newCustomer));
        System.out.println(newCustomer.geteMail());
        System.out.println(newCustomer.geteMail());
        return userMapper.mapToCustomeDto(newUser);
    }

    public ShowOrderDto addOrder(String userId, String authorization, CreateItemGroupDto[] newOrders) {
        securityService.validateAuthorization(authorization, Feature.ORDER);
        Order newOrder = new Order(userId);
        Arrays.stream(newOrders).forEach(itemGroupDto -> newOrder.addToItemGroupList(orderMapper.mapToItemGroup(itemGroupDto)));
        return orderMapper.mapToShowOrderDto(newOrder);
    }

    public List<CustomerDto> getAllUsers(String authorization) {
        securityService.validateAuthorization(authorization, Feature.VIEW_USERS);
        return userRepository.getAllUsers().stream()
                .map(userMapper::mapToCustomeDto)
                .collect(Collectors.toList());
    }
}

package com.switchfully.eurder.service;

import com.switchfully.eurder.api.OrderMapper;
import com.switchfully.eurder.api.UserMapper;
import com.switchfully.eurder.api.dtos.*;
import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.domain.users.Feature;
import com.switchfully.eurder.domain.users.User;
import com.switchfully.eurder.repositories.ItemRepository;
import com.switchfully.eurder.repositories.OrderRepository;
import com.switchfully.eurder.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;


    public UserService(SecurityService securityService, UserRepository userRepository, ItemRepository itemRepository, UserMapper userMapper, OrderMapper orderMapper, OrderRepository orderRepository) {
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    public CustomerDto addCustomer(CreateCustomerDto newCustomer) {
        if (userRepository.eMailAlreadyExits(newCustomer.geteMail())) throw new IllegalArgumentException("This email address is already in use.");
        User newUser = userRepository.addCustomer(userMapper.mapToUser(newCustomer));
        return userMapper.mapToCustomeDto(newUser);
    }

    public ShowOrderDto addOrder(String userId, String authorization, CreateItemGroupDto[] newOrders) {
        securityService.validateAuthorization(authorization, Feature.ORDER);
        assertUserExists(userId);
        Order newOrder = new Order(userId);
        Arrays.stream(newOrders).forEach(itemGroupDto -> {
            assertItemExits(itemGroupDto.getItemId());
            newOrder.addToItemGroupList(orderMapper.mapToItemGroup(itemGroupDto));
        });
        orderRepository.addNewOrder(newOrder);
        return orderMapper.mapToShowOrderDto(newOrder);
    }

    public List<CustomerDto> getAllUsers(String authorization) {
        securityService.validateAuthorization(authorization, Feature.VIEW_USERS);
        return userRepository.getAllUsers().stream()
                .map(userMapper::mapToCustomeDto)
                .collect(Collectors.toList());
    }

    public ShowUserDto getCustomer(String authorization, String customerId) {
        securityService.validateAuthorization(authorization, Feature.VIEW_USERS);
        assertUserExists(customerId);
        return userMapper.maptoShowUserDto(userRepository.getUserById(customerId));
    }

    private void assertUserExists(String userId) {
        if(userRepository.getUserById(userId)==null) throw new IllegalArgumentException("There is no customer with the id "+ userId +".");
    }
    private void assertItemExits(String itemId) {
        if(itemRepository.getItemMap().get(itemId)==null) throw new IllegalArgumentException("Item with the id "+ itemId +" does not exist.");
    }


    public ShowAllOrdersDto getCustomerOrders(String authorization, String customerId) {
        securityService.validateAuthorization(authorization, Feature.ORDER);
        assertUserExists(customerId);
        List<Order> allOrdersOfCustomer=orderRepository.getOrdersById(customerId);
        return orderMapper.mapToShowAllOrders(allOrdersOfCustomer);
    }
}

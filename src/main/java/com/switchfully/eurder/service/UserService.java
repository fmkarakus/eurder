package com.switchfully.eurder.service;

import com.switchfully.eurder.api.UserMapper;
import com.switchfully.eurder.api.dtos.CreateCustomerDto;
import com.switchfully.eurder.api.dtos.CustomerDto;
import com.switchfully.eurder.domain.User;
import com.switchfully.eurder.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
private final SecurityService securityService;
private final UserRepository userRepository;
private final UserMapper userMapper;

    public UserService(SecurityService securityService, UserRepository userRepository, UserMapper userMapper) {
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public CustomerDto addCustomer(CreateCustomerDto newCustomer) {
        User newUser=userRepository.addCustomer(userMapper.mapToUser(newCustomer));
        System.out.println(newCustomer.geteMail());
        System.out.println(newCustomer.geteMail());
        return userMapper.mapToCustomeDto(newUser);
    }
}

package com.switchfully.eurder.api;

import com.switchfully.eurder.api.dtos.CreateCustomerDto;
import com.switchfully.eurder.api.dtos.CustomerDto;
import com.switchfully.eurder.domain.users.Address;
import com.switchfully.eurder.domain.users.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(CreateCustomerDto dto) {
        return new User(dto.getFirstName(), dto.getLastName(), dto.geteMail(), new Address(dto.getStreet(), dto.getHouseNumber(), dto.getPostCode(), dto.getCity()), dto.getPhoneNumber(), dto.getPassword());
    }

    public CustomerDto mapToCustomeDto(User customer){
        return new CustomerDto(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.geteMail(), customer.getAddress(), customer.getPhoneNumber(),customer.getRole());
    };
}

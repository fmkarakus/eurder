package com.switchfully.eurder.service.user;

import com.switchfully.eurder.service.user.OrderMapper;
import com.switchfully.eurder.service.user.userDto.CreateCustomerDto;
import com.switchfully.eurder.service.user.userDto.CustomerDto;
import com.switchfully.eurder.service.user.orderDto.ShowOrderDto;
import com.switchfully.eurder.service.user.userDto.ShowUserDto;
import com.switchfully.eurder.domain.users.Address;
import com.switchfully.eurder.domain.users.Person;
import com.switchfully.eurder.domain.users.PostalCode;
import com.switchfully.eurder.repositories.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public UserMapper(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public Person mapToUser(CreateCustomerDto dto) {
        return new Person(dto.getFirstName(), dto.getLastName(), dto.geteMail(), new Address(dto.getStreet(), dto.getHouseNumber(),new PostalCode(dto.getPostCode(), dto.getCity())), dto.getPhoneNumber(), dto.getPassword());
    }

    public CustomerDto mapToCustomeDto(Person customer){
        return new CustomerDto(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.geteEmail(), customer.getAddress(), customer.getPhoneNumber(),customer.getRole());
    };

    public ShowUserDto maptoShowUserDto(Person customer){
        List<ShowOrderDto> ordersOfCustomer=orderRepository.findAll().stream()
                .filter(order -> order.getCustomer().getId()==customer.getId())
                .map(orderMapper::mapToShowOrderDto)
                .collect(Collectors.toList());
        return new ShowUserDto(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.geteEmail(), customer.getAddress(), customer.getPhoneNumber(), ordersOfCustomer);
    }
}

package com.switchfully.eurder.service.user;

import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.repositories.ItemGroupRepository;
import com.switchfully.eurder.service.user.orderDto.CreateItemGroupDto;
import com.switchfully.eurder.service.user.orderDto.ShowAllOrdersDto;
import com.switchfully.eurder.service.user.orderDto.ShowOrderDto;
import com.switchfully.eurder.domain.users.Person;
import com.switchfully.eurder.service.user.orderDto.TodaysOrderDto;
import com.switchfully.eurder.service.user.userDto.CreateCustomerDto;
import com.switchfully.eurder.service.user.userDto.CustomerDto;
import com.switchfully.eurder.service.user.userDto.ShowUserDto;
import com.switchfully.eurder.repositories.OrderRepository;
import com.switchfully.eurder.repositories.PersonRepository;
import com.switchfully.eurder.service.item.ItemService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final ItemService itemService;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;
    private final ItemGroupRepository itemGroupRepository;

    public UserService(ItemService itemService, UserMapper userMapper, OrderMapper orderMapper, OrderRepository orderRepository, PersonRepository personRepository, ItemGroupRepository itemGroupRepository) {
        this.itemService = itemService;
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.personRepository = personRepository;
        this.itemGroupRepository = itemGroupRepository;
    }


    public CustomerDto addCustomer(CreateCustomerDto newCustomer) {
        if (personRepository.findByEmail(newCustomer.geteMail()).isPresent())
            throw new IllegalArgumentException("This email address is already in use.");
        Person newPerson=personRepository.save(userMapper.mapToUser(newCustomer));
        return userMapper.mapToCustomeDto(newPerson);
    }

    public ShowOrderDto addOrder(long userId,CreateItemGroupDto[] newOrders) {
        Person customer= getCustomerById(userId);
        List<ItemGroup> itemGroupList = new ArrayList<>();
        Arrays.stream(newOrders).forEach(itemGroupDto -> {
            assertItemExits(itemGroupDto.getItemId());
            ItemGroup itemGroup = orderMapper.mapToItemGroup(itemGroupDto);
            itemGroupList.add(itemGroup);
        });
        Order newOrder = new Order(customer, itemGroupList);
        orderRepository.save(newOrder);
        return orderMapper.mapToShowOrderDto(newOrder);
    }

    private Person getCustomerById(long userId) {
        return personRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("There is no customer with the id " + userId + "."));
    }

    private Order getOrderById(long orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("There is no order with the id " + orderId + "."));
    }

    public List<CustomerDto> getAllUsers() {
        return personRepository.findAll().stream()
                .map(userMapper::mapToCustomeDto)
                .collect(Collectors.toList());
    }

    public ShowUserDto getCustomer(long customerId) {
        Person customer= getCustomerById(customerId);
        return userMapper.maptoShowUserDto(customer);
    }


    private void assertItemExits(long itemId) {
        itemService.getItemById(itemId);
    }


    public ShowAllOrdersDto getCustomerOrders(long customerId) {
        Person customer= getCustomerById(customerId);
        List<Order> allOrdersOfCustomer = orderRepository.findAllByCustomer(customer);
        return orderMapper.mapToShowAllOrders(allOrdersOfCustomer);
    }

    public List<TodaysOrderDto> getTodaysOrders() {
        List<ItemGroup> itemGroups=itemGroupRepository.findAllByShippingDate(LocalDate.now());
        List<TodaysOrderDto> todaysOrderDto=new ArrayList<>();
        itemGroups.forEach(itemGroup -> {
                Order order=getOrderById(itemGroup.getId());
                Person customer=getCustomerById(order.getCustomer().getId());
                todaysOrderDto.add(orderMapper.mapToTodaysOrderDto(itemGroup,customer));
        });
        return todaysOrderDto;
    }
}

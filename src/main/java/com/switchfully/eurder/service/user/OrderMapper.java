package com.switchfully.eurder.service.user;

import com.switchfully.eurder.domain.users.Person;
import com.switchfully.eurder.service.user.orderDto.*;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.item.ItemService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final ItemService itemService;


    public OrderMapper(ItemService itemService) {
        this.itemService = itemService;

    }


    public ItemGroup mapToItemGroup(CreateItemGroupDto dto) {
        return new ItemGroup(itemService.getItemById(dto.getItemId()), dto.getAmount(), setShippingDate(dto.getItemId(),dto.getAmount()), setTotalPrice(dto));
    }

    private double setTotalPrice(CreateItemGroupDto dto) {
        double priceOfItem = itemService.getItemById(dto.getItemId()).getPrice();
        return priceOfItem * dto.getAmount();
    }

    private LocalDate setShippingDate(long itemId, int amount) {
        if ((itemService.getItemById(itemId).getAmount() - amount )< 0) return LocalDate.now().plusDays(7);
        return LocalDate.now().plusDays(1);
    }

    public ShowOrderDto mapToShowOrderDto(Order order) {
        List<ShowItemGroupDto> itemGroupDtos = new ArrayList<>();
        order.getItemGroupList().forEach(itemGroup -> itemGroupDtos.add(mapToShowItemGroupDto(itemGroup)));
        return new ShowOrderDto(order.getId(), itemGroupDtos, order.getTotalPrice());
    }

    private ShowItemGroupDto mapToShowItemGroupDto(ItemGroup itemGroup) {
        return new ShowItemGroupDto(itemGroup.getItem().getId(), itemGroup.getAmount(), itemGroup.getShippingDate(), itemGroup.getTotalPrice());
    }

    public ShowAllOrdersDto mapToShowAllOrders(List<Order> orders) {
        return new ShowAllOrdersDto(orders.stream().map(this::mapToShowOrderDto).collect(Collectors.toList()), orders.stream().mapToDouble(Order::getTotalPrice).sum());
    }

    public TodaysOrderDto mapToTodaysOrderDto(ItemGroup itemGroup, Person customer) {
        return new TodaysOrderDto(itemGroup.getId(), itemGroup.getAmount(), customer.getAddress());
    }

    public CreateItemGroupDto mapToCreateItemGroupDto(ItemGroup itemGroup) {
        return new CreateItemGroupDto(itemGroup.getItem().getId(), itemGroup.getAmount());
    }
}

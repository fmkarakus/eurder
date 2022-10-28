package com.switchfully.eurder.api;

import com.switchfully.eurder.api.dtos.CreateItemGroupDto;
import com.switchfully.eurder.api.dtos.ShowItemGroupDto;
import com.switchfully.eurder.api.dtos.ShowOrderDto;
import com.switchfully.eurder.domain.ItemGroup;
import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.repositories.ItemRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {
    private final ItemRepository itemRepository;

    public OrderMapper(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemGroup mapToItemGroup(CreateItemGroupDto dto) {
        return new ItemGroup(dto.getItemId(), dto.getAmount(), setShippingDate(dto.getItemId()), setTotalPrice(dto));
    }

    private double setTotalPrice(CreateItemGroupDto dto) {
        return itemRepository.getItemMap().get(dto.getItemId()).getPrice() * dto.getAmount();
    }

    private LocalDate setShippingDate(String itemId) {
        if (itemRepository.getItemMap().get(itemId).getAmount() == 0) return LocalDate.now().plusDays(7);
        return LocalDate.now().plusDays(1);
    }

    public ShowOrderDto mapToShowOrderDto(Order order) {
        List<ShowItemGroupDto> itemGroupDtos=new ArrayList<>();
        order.getItemGroupList().forEach(itemGroup -> itemGroupDtos.add(mapToShowItemGroupDto(itemGroup)));
        return new ShowOrderDto(order.getId(), itemGroupDtos, order.getTotalPrice());
    }

    private ShowItemGroupDto mapToShowItemGroupDto(ItemGroup itemGroup) {
        return new ShowItemGroupDto(itemGroup.getItemId(), itemGroup.getAmount(), itemGroup.getShippingDate());
    }

}
package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.Item;
import org.springframework.stereotype.Component;


@Component
public class ItemMapper {
    public Item mapToItem(CreateItemDto createItemDto){
        return new Item(createItemDto.getName(), createItemDto.getDescription(), createItemDto.getPrice(), createItemDto.getAmount());
    }

    public ItemDto mapToItemDto(Item item){
        return new ItemDto(item.getId(), item.getName(), item.getDescription(),item.getPrice(),item.getAmount());
    }
}

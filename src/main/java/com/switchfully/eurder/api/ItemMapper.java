package com.switchfully.eurder.api;

import com.switchfully.eurder.api.dtos.CreateItemDto;
import com.switchfully.eurder.api.dtos.CreateItemGroupDto;
import com.switchfully.eurder.api.dtos.ItemDto;
import com.switchfully.eurder.api.dtos.UpdateItemDto;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.ItemGroup;
import com.switchfully.eurder.repositories.ItemRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class ItemMapper {

    public Item mapToItem(CreateItemDto createItemDto){
        return new Item(createItemDto.getName(), createItemDto.getDescription(), createItemDto.getPrice(), createItemDto.getAmount());
    }

    public ItemDto mapToItemDto(Item item){
        return new ItemDto(item.getId(), item.getName(), item.getDescription(),item.getPrice(),item.getAmount());
    }

    public Item mapUpdateItemDtoToItem(UpdateItemDto updatedItem) {
        return new Item(updatedItem.getName(), updatedItem.getDescription(), updatedItem.getPrice(), updatedItem.getAmount());
    }
}

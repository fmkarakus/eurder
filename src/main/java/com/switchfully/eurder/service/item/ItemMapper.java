package com.switchfully.eurder.service.item;

import com.switchfully.eurder.service.item.dto.CreateItemDto;
import com.switchfully.eurder.service.item.dto.ItemDto;
import com.switchfully.eurder.service.item.dto.StockDto;
import com.switchfully.eurder.service.item.dto.UpdateItemDto;
import com.switchfully.eurder.domain.item.Item;
import org.springframework.stereotype.Component;


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

    public StockDto mapToStockDto(Item item) {
        return new StockDto(item.getId(), item.getStockStatus());
    }
}

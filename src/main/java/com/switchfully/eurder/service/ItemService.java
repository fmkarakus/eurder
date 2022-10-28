package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dtos.CreateItemDto;
import com.switchfully.eurder.api.dtos.ItemDto;
import com.switchfully.eurder.api.ItemMapper;
import com.switchfully.eurder.api.dtos.UpdateItemDto;
import com.switchfully.eurder.domain.users.Feature;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private final SecurityService securityService;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(SecurityService securityService, ItemRepository itemRepository, ItemMapper itemMapper) {
        this.securityService = securityService;
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public ItemDto addNewItem(String authorization, CreateItemDto newItem) {
        securityService.validateAuthorization(authorization, Feature.ADD_NEW_ITEM);
        Item item = itemRepository.addNewItem(itemMapper.mapToItem(newItem));
        return itemMapper.mapToItemDto(item);
    }

    public List<ItemDto> getAllItems(String authorization) {
        securityService.validateAuthorization(authorization, Feature.GET_ALL_ITEMS);
        List<ItemDto> items = new ArrayList<>();
        itemRepository.getItemMap().values().forEach(item -> items.add(itemMapper.mapToItemDto(item)));
        return items;
    }

    public ItemDto updateItem(String authorization, String itemId, UpdateItemDto updatedItem) {
        securityService.validateAuthorization(authorization, Feature.UPDATE_ITEMS);
        assertItemExits(itemId);
        Item item = itemRepository.updateItem(itemId, itemMapper.mapUpdateItemDtoToItem(updatedItem));
        return itemMapper.mapToItemDto(item);
    }

    private void assertItemExits(String itemId) {
        if (itemRepository.getItemMap().get(itemId) == null)
            throw new IllegalArgumentException("Item with the id " + itemId + " does not exist.");
    }
}

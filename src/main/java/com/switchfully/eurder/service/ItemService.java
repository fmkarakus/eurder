package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dtos.CreateItemDto;
import com.switchfully.eurder.api.dtos.ItemDto;
import com.switchfully.eurder.api.mappers.ItemMapper;
import com.switchfully.eurder.api.dtos.UpdateItemDto;
import com.switchfully.eurder.domain.users.Feature;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        return itemRepository.getAllItems()
                .stream()
                .map(itemMapper::mapToItemDto)
                .collect(Collectors.toList());

    }

    public ItemDto updateItem(String authorization, String itemId, UpdateItemDto updateItemDto) {
        securityService.validateAuthorization(authorization, Feature.UPDATE_ITEMS);
        assertItemExits(itemId);
        Item updateItem = itemMapper.mapUpdateItemDtoToItem(updateItemDto);
        Item item=itemRepository.getById(itemId).updateItem(updateItem);
        return itemMapper.mapToItemDto(item);
    }

    private void assertItemExits(String itemId) {
        if (itemRepository.getById(itemId) == null)
            throw new IllegalArgumentException("Item with the id " + itemId + " does not exist.");
    }
}

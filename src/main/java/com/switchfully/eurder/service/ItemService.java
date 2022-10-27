package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dtos.CreateItemDto;
import com.switchfully.eurder.api.dtos.ItemDto;
import com.switchfully.eurder.api.ItemMapper;
import com.switchfully.eurder.domain.Feature;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.repositories.ItemRepository;
import org.springframework.stereotype.Service;

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
        Item item=itemRepository.addNewItem(itemMapper.mapToItem(newItem));
        return itemMapper.mapToItemDto(item);
    }
}

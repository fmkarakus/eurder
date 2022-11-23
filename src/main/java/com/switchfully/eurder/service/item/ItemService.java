package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.StockStatus;
import com.switchfully.eurder.service.item.dto.CreateItemDto;
import com.switchfully.eurder.service.item.dto.ItemDto;
import com.switchfully.eurder.service.item.dto.StockDto;
import com.switchfully.eurder.service.item.dto.UpdateItemDto;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public ItemDto addNewItem(CreateItemDto newItem) {
        Item item = itemRepository.save(itemMapper.mapToItem(newItem));
        return itemMapper.mapToItemDto(item);
    }

    public List<ItemDto> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(itemMapper::mapToItemDto)
                .collect(Collectors.toList());

    }

    public ItemDto updateItem(long itemId, UpdateItemDto updateItemDto) {
        Item updateItem = itemMapper.mapUpdateItemDtoToItem(updateItemDto);
        Item item = getItemById(itemId);
        item.updateItem(updateItem);
        return itemMapper.mapToItemDto(item);
    }

    public Item getItemById(long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Item with the id " + itemId + " does not exist."));
    }


    public List<StockDto> getStockStatus() {
        List<Item> allItems = itemRepository.findAll();
        allItems.forEach(Item::setStockStatus);
        return getStockDtos(allItems);
    }

    private List<StockDto> getStockDtos(List<Item> allItems) {
        return allItems.stream()
                .sorted(Comparator.comparingInt(Item::getAmount))
                .map(itemMapper::mapToStockDto)
                .toList();
    }

    public List<StockDto> getStockStatusFilteredBy(StockStatus status) {
        if(status==null) return getStockStatus();
        List<Item> allItems = itemRepository.findAll();
        allItems.forEach(Item::setStockStatus);
        return getStockDtos(allItems.stream().filter(item -> item.getStockStatus().equals(status)).collect(Collectors.toList()));
    }
}

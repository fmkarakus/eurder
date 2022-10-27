package com.switchfully.eurder.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class ItemRepository {
    private final Map<String, Item> itemMap;

    public ItemRepository() {
        this.itemMap = new HashMap<>();
    }

    public Item addNewItem(Item item) {
       itemMap.put(item.getId(),item);
       return item;
    }
}

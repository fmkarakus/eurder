package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository {
    private final Map<String, Item> itemMap;

    public ItemRepository() {
        this.itemMap = new HashMap<>();
        addSomeItems();
    }

    private void addSomeItems() {
        Item item1 = new Item("item1", "test item", 2.5, 10);
        Item item2 = new Item("item2", "test item", 3.5, 8);
        Item item3 = new Item("item3", "test item", 4.5, 5);
        Item item4 = new Item("item3", "test item", 4.5, 0);
        itemMap.put(item1.getId(), item1);
        itemMap.put(item2.getId(), item2);
        itemMap.put(item3.getId(), item3);
        itemMap.put(item4.getId(), item4);
    }

    public Item addNewItem(Item item) {
        itemMap.put(item.getId(), item);
        return item;
    }

    public Map<String, Item> getItemMap() {
        return itemMap;
    }

    public Item getById(String itemId) {
        return itemMap.get(itemId);
    }

    public Collection<Item> getAllItems() {
        return itemMap.values();
    }
}

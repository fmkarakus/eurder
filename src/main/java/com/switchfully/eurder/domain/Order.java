package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private final String customerId;
    private final List<ItemGroup> itemGroupList;
    private double totalPrice=0;

    public Order(String customerId) {
        this.customerId = customerId;
        this.id = UUID.randomUUID().toString();
        this.itemGroupList =new ArrayList<>();
    }

    public void addToItemGroupList(ItemGroup itemGroup){
        itemGroupList.add(itemGroup);
        totalPrice+=itemGroup.getTotalPrice();
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<ItemGroup> getItemGroupList() {
        return itemGroupList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}

package com.switchfully.eurder.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private final String customerId;
    private final List<ItemGroup> itemGroupList;
    private double totalPrice = 0;

    public Order(String customerId, List<ItemGroup> itemGroupList) {
        this.customerId = customerId;
        this.id = UUID.randomUUID().toString();
        this.itemGroupList = itemGroupList;
        totalPrice = itemGroupList.stream().mapToDouble(ItemGroup::getTotalPrice).sum();
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

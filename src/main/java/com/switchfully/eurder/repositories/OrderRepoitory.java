package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderRepoitory {
    private final Map<String, Order> orderMap;

    public OrderRepoitory() {
        this.orderMap = new HashMap<>();
    }

    public Order addNewOrder(Order order) {
        orderMap.put(order.getId(),order);
        return order;
    }
}

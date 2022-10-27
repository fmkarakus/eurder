package com.switchfully.eurder.api.dtos;

import java.util.List;

public class ShowOrderDto {
    private final String orderId;
    private List<ShowItemGroupDto> orders;

    private double totalPrice;

    public ShowOrderDto(String orderId, List<ShowItemGroupDto> orders, double totalPrice) {
        this.orderId = orderId;
        this.orders = orders;
        this.totalPrice = totalPrice;
    }

    public List<ShowItemGroupDto> getOrders() {
        return orders;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }
}

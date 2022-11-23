package com.switchfully.eurder.service.user.orderDto;

import java.util.List;

public class ShowOrderDto {
    private final long orderId;
    private List<ShowItemGroupDto> orders;

    private double totalPrice;

    public ShowOrderDto(long orderId, List<ShowItemGroupDto> orders, double totalPrice) {
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

    public long getOrderId() {
        return orderId;
    }
}

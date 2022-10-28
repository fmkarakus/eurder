package com.switchfully.eurder.api.dtos;

import java.util.List;

public class ShowAllOrdersDto {
    private List<ShowOrderDto> orders;

    private double totalPrice;

    public ShowAllOrdersDto(List<ShowOrderDto> orders, double totalPrice) {
        this.orders = orders;
        this.totalPrice = totalPrice;
    }

    public List<ShowOrderDto> getOrders() {
        return orders;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}

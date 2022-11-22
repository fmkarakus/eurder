package com.switchfully.eurder.api.dtos;

import java.time.LocalDate;

public class ShowItemGroupDto {
    private long itemId;
    private int amount;
    private LocalDate shippingDate;
    private double price;

    public double getPrice() {
        return price;
    }

    public ShowItemGroupDto(long itemId, int amount, LocalDate shippingDate, double price) {
        this.itemId = itemId;
        this.amount = amount;
        this.shippingDate = shippingDate;
        this.price = price;
    }

    public long getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }
}

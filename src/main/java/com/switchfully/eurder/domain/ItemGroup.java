package com.switchfully.eurder.domain;

import java.time.LocalDate;

public class ItemGroup {
    private final String  itemId;
    private final int amount;
    private final LocalDate shippingDate;
    private final double totalPrice;

    public ItemGroup(String itemId, int amount, LocalDate shippingDate, double totalPrice) {
        this.itemId = itemId;
        this.amount = amount;
        this.shippingDate = shippingDate;
        this.totalPrice = totalPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}

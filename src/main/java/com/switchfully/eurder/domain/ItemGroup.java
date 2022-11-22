package com.switchfully.eurder.domain;

import java.time.LocalDate;

public class ItemGroup {
    private final Item  item;
    private final int amount;
    private final LocalDate shippingDate;
    private final double totalPrice;

    public ItemGroup(Item item, int amount, LocalDate shippingDate, double totalPrice) {
        this.item = item;
        this.amount = amount;
        this.shippingDate = shippingDate;
        this.totalPrice = totalPrice;
    }


    public Item getItem() {
        return item;
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

    public void decreaseStock(int amount) {
        item.decreaseStock(amount);
    }
}

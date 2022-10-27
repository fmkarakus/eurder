package com.switchfully.eurder.api.dtos;

import java.time.LocalDate;

public class ShowItemGroupDto {
    private String itemId;
    private int amount;
    private LocalDate shippingDate;

    public ShowItemGroupDto(String itemId, int amount, LocalDate shippingDate) {
        this.itemId = itemId;
        this.amount = amount;
        this.shippingDate = shippingDate;
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
}

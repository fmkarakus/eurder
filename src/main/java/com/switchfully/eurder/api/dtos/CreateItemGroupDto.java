package com.switchfully.eurder.api.dtos;

public class CreateItemGroupDto {
    private String itemId;
    private int amount;

    public CreateItemGroupDto(String itemId, int amount) {
        this.itemId = itemId;
        this.amount = amount;
    }

    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }
}

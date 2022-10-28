package com.switchfully.eurder.api.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CreateItemGroupDto {
    @NotBlank(message = "Item Id is required")
    private String itemId;
    @Min(1)
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

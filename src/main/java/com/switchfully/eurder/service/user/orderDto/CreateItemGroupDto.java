package com.switchfully.eurder.service.user.orderDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CreateItemGroupDto {
    @NotBlank(message = "Item Id is required")
    private long itemId;
    @Min(1)
    private int amount;

    public CreateItemGroupDto(long itemId, int amount) {
        this.itemId = itemId;
        this.amount = amount;
    }

    public long getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }
}

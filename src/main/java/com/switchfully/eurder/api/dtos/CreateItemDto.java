package com.switchfully.eurder.api.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateItemDto {
    @NotBlank(message = "Name of item cannot be blank")
    private String name;
    private String description;

    @Min(0)
    private double price;

    @Min(0)
    private int amount;

    public CreateItemDto(String name, String description, double price, int amount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}

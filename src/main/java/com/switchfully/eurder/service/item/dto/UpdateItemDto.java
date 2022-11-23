package com.switchfully.eurder.service.item.dto;

public class UpdateItemDto {
    private String name;
    private String description;
    private double price;
    private int amount;

    public UpdateItemDto(String name, String description, double price, int amount) {
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

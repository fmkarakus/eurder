package com.switchfully.eurder.api.dtos;

public class ItemDto {
    private final String id;
    private final String name;
    private final String description;
    private double price;
    private int amount;

    public ItemDto(String id, String name, String description, double price, int amount) {
        this.price = price;
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }
}

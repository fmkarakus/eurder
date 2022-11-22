package com.switchfully.eurder.domain;

import java.util.UUID;

public class Item {
    private final String id;
    private String name;
    private String description;
    private double price;
    private int amount;

    public Item(String name, String description, double price, int amount) {
        this.price = price;
        this.id = UUID.randomUUID().toString();
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

    public void decreaseStock(int amount) {
        this.amount -= amount;
    }
    public void increaseStock(int amount) {
        this.amount += amount;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public Item updateItem(Item updateItem) {
        if (updateItem.getName() != null && !updateItem.getName().isBlank()) setName(getName());
        if (updateItem.getDescription() != null && !updateItem.getDescription().isBlank())
            setDescription(getDescription());
        if (updateItem.getPrice() > 0) setPrice(getPrice());
        if (updateItem.getAmount() > 0) increaseStock(getAmount());
        return this;
    }
}

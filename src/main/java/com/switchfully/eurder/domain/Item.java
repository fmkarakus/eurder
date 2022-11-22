package com.switchfully.eurder.domain;

import javax.persistence.*;

@Entity
@Table(name="item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
    @Column(name="id")
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="price")
    private double price;
    @Column(name="amount")
    private int amount;

    public Item(String name, String description, double price, int amount) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    public Item() {

    }

    public long getId() {
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
        if (updateItem.getName() != null && !updateItem.getName().isBlank()) setName(updateItem.getName());
        if (updateItem.getDescription() != null && !updateItem.getDescription().isBlank())
            setDescription(updateItem.getDescription());
        if (updateItem.getPrice() > 0) setPrice(updateItem.getPrice());
        if (updateItem.getAmount() > 0) increaseStock(updateItem.getAmount());
        return this;
    }
}

package com.switchfully.eurder.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "item_group")
public class ItemGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_group_seq")
    @SequenceGenerator(name = "item_group_seq", sequenceName = "item_group_seq", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;
    @Column(name="amount")
    private int amount;
    @Column(name="shipping_time")
    private LocalDate shippingDate;
    @Column(name="total_price")
    private double totalPrice;

    public ItemGroup(Item item, int amount, LocalDate shippingDate, double totalPrice) {
        this.item = item;
        this.amount = decreaseStock(amount);
        this.shippingDate = shippingDate;
        this.totalPrice = totalPrice;
    }

    public ItemGroup() {

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

    public int decreaseStock(int amount) {
        item.decreaseStock(amount);
        return amount;
    }
}

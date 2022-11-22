package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.users.Person;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "orders_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Person customer;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name="orders_id")
    private  List<ItemGroup> itemGroupList;
    @JoinColumn(name="total_price")
    private double totalPrice = 0;

    public Order(Person customer, List<ItemGroup> itemGroupList) {
        this.customer = customer;
        this.itemGroupList = itemGroupList;
        totalPrice = itemGroupList.stream().mapToDouble(ItemGroup::getTotalPrice).sum();
    }

    public Order() {

    }


    public long getId() {
        return id;
    }

    public Person getCustomer() {
        return customer;
    }

    public List<ItemGroup> getItemGroupList() {
        return itemGroupList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}

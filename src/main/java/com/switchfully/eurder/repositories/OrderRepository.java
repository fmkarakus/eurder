package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.users.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    private final Map<String, Order> orderMap;
//
//    public OrderRepository() {
//        this.orderMap = new HashMap<>();
//    }
//
//    public Order addNewOrder(Order order) {
//        orderMap.put(order.getId(),order);
//        return order;
//    }
//
//    public Collection<Order> getAllOrders() {
//        return orderMap.values();
//    }
//
     List<Order> findAllByCustomer(Person customer);

}

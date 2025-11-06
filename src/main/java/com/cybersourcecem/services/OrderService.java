package com.cybersourcecem.services;

import com.cybersourcecem.entities.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order) ;
    void delete(long id);
    Order editOrder(Order order, long id);
    List<Order> findAllOrder();
    Order findOrder(long id);
    void deleteAll();
}

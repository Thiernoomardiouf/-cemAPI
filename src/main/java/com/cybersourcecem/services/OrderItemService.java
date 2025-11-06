package com.cybersourcecem.services;

import com.cybersourcecem.entities.OrderItem;

import java.util.List;

public interface OrderItemService {
    void createOrderItem(OrderItem orderItem);
    List<OrderItem> findAll();
    void editOrderItem(OrderItem orderItem, long id);
    void delete(long id);
    void deleteAll();
}

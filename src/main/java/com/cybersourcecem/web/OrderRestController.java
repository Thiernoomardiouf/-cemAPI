package com.cybersourcecem.web;

import com.cybersourcecem.entities.Order;
import com.cybersourcecem.models.OrderModel;
import com.cybersourcecem.services.OrderService;
import com.cybersourcecem.services.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderRestController {
    final private OrderService orderService;
    final  private PersonService personService;
    OrderRestController(final OrderService orderService, final PersonService personService) {
        this.personService = personService;
        this.orderService = orderService;
    }
    @PostMapping("/orders")
    void saveOrder(@RequestBody OrderModel orderModel){
        Order order = new Order();
        order.setClient(personService.findOneClientById(orderModel.getClientId()));
        order.setDate(new Date());
        order.setOrderNumber(orderModel.getOrderNumber());
        order.setPayment(null);
        order.setMountTotal(0);
        orderService.createOrder(order);
    }
    @GetMapping("/orders")
    List<Order> findAll() {
        return this.orderService.findAllOrder();
    }

    @DeleteMapping("/orders")
    void deleteAll() {
        this.orderService.deleteAll();
    }
}

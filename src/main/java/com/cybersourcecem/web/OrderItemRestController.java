package com.cybersourcecem.web;

import com.cybersourcecem.entities.Order;
import com.cybersourcecem.entities.OrderItem;
import com.cybersourcecem.entities.Product;
import com.cybersourcecem.models.OrderItemModel;
import com.cybersourcecem.models.OrderProductModel;
import com.cybersourcecem.services.OrderItemService;
import com.cybersourcecem.services.OrderService;
import com.cybersourcecem.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class OrderItemRestController {
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ProductService productService;

    OrderItemRestController(
            final OrderItemService orderItemService,
            final OrderService orderService,
            final ProductService productService
    ) {
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @PostMapping("/orders/lines")
    Order createLineOrder(@RequestBody OrderItemModel orderItemModel) {
        Order order = this.orderService.findOrder(orderItemModel.getOrderId());
        double total = 0;
        if (order != null) {
            for (OrderProductModel orderProductModel : orderItemModel.getProducts()) {
                OrderItem orderItem = new OrderItem();

                orderItem.setOrder(order);

                Product product = productService.findProduct(orderProductModel.getId());

                orderItem.setProduct(product);

                orderItem.setPrice(product.getPrice());

                orderItem.setQuantity(orderProductModel.getQuantity());

                orderItemService.createOrderItem(orderItem);

                total += orderProductModel.getQuantity() * product.getPrice();
            }
            // on met a jour le montant total de la commande
           // order.setMountTotal(total);

            return orderService.editOrder(order, order.getId());
        }
        return null;
    }

    @GetMapping("/orders/lines")
    List<OrderItem> findAll() {
        return this.orderItemService.findAll();
    }

    @DeleteMapping("/orders/lines")
    void deleteAll() {
        this.orderItemService.deleteAll();
    }

}

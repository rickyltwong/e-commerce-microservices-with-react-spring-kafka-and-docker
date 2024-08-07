package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.service.OrderPublisher;
import com.ecommerce.orderservice.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
//import java.util.UUID;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    private final OrderPublisher orderPublisher;

    @Autowired
    public OrderController(OrderServiceImpl orderService, OrderPublisher orderPublisher) {
        this.orderService = orderService;
        this.orderPublisher = orderPublisher;
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("Hello from test");
        return "Hello from test";
    }


    @GetMapping("/kafka-test")
    public String kafkaTest() {
        System.out.println("Hello from kafka-test");


            UUID orderId = UUID.randomUUID();
            Timestamp orderDate = new Timestamp(System.currentTimeMillis());
            BigDecimal totalAmount = new BigDecimal("150.00");

            OrderItem item1 = new OrderItem(UUID.randomUUID(), null, UUID.randomUUID(), 2);
            OrderItem item2 = new OrderItem(UUID.randomUUID(), null, UUID.randomUUID(), 3);

            List<OrderItem> orderItems = Arrays.asList(item1, item2);

            Order order = new Order(orderId, orderDate, totalAmount, orderItems);

            // Setting the order reference in each OrderItem
            for (OrderItem item : orderItems) {
                item.setOrder(order);
            }

            orderPublisher.publishOrder(order);



        return "Hello from kafka-test";
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        orderPublisher.publishOrder(createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }
}
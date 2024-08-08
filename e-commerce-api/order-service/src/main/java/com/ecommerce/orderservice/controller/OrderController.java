package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderMessage;
import com.ecommerce.orderservice.service.OrderPublisher;
import com.ecommerce.orderservice.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
        Order testOrder = new Order();
        testOrder.setOrderId(UUID.randomUUID());
        testOrder.setTotalAmount(BigDecimal.valueOf(100));
        testOrder.setOrderDate(new Timestamp(System.currentTimeMillis()));
        orderPublisher.publishOrder(new OrderMessage(testOrder.getOrderId().toString(), "Order created"));
        return "Hello from kafka-test";
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        orderPublisher.publishOrder(new OrderMessage(createdOrder.getOrderId().toString(), "Order created"));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }
}
package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderMessage;
import com.ecommerce.orderservice.service.OrderPublisher;
import com.ecommerce.orderservice.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

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
        Order testOrder = new Order();
        testOrder.setOrderId(UUID.randomUUID());
        testOrder.setTotalAmount(BigDecimal.valueOf(100));
        testOrder.setOrderDate(new Timestamp(System.currentTimeMillis()));
        orderPublisher.testPublishOrder(new OrderMessage(testOrder.getOrderId().toString(), "Order created from kafka-test"));
        return "Kafka test successful!";
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Order createdOrder = orderService.placeOrder(orderRequest.getOrderItems());
        orderPublisher.publishOrder(createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
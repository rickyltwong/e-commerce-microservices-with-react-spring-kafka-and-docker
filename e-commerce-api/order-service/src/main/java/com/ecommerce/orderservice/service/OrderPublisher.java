package com.ecommerce.orderservice.service;

import org.slf4j.*;
import com.ecommerce.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderPublisher {

    private final StreamBridge streamBridge;

    @Autowired
    public OrderPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishOrder(Order order) {
        System.out.println("Publish Order triggered");
        Message<Order> message = MessageBuilder.withPayload(order).build();
        streamBridge.send("order-out-0", message);
    }
}
package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.model.OrderMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;

import org.springframework.stereotype.Service;

@Service
public class OrderPublisher {

    private final StreamBridge streamBridge;

    @Autowired
    public OrderPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishOrder(OrderMessage orderMessage) {
        System.out.println("Publishing order message.............");
        streamBridge.send("producer-out-0", orderMessage);
    }
}
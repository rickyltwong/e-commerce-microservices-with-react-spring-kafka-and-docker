package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.model.Order;
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

    public void testPublishOrder(OrderMessage orderMessage) {
        System.out.println("Publishing order message.............");
        streamBridge.send("producer-out-0", orderMessage);
    }

    public void publishOrder(Order order) {
        OrderRequest orderPlacedEvent = new OrderRequest();
        orderPlacedEvent.setOrderId(order.getOrderId());
        orderPlacedEvent.setOrderItems(order.getOrderItems().stream()
                .map(orderItem -> new OrderRequest.OrderItemRequest(
                        orderItem.getProductId(),
                        orderItem.getQuantity()
                ))
                .toList());

        streamBridge.send("orderPlaced-out-0", orderPlacedEvent);
    }
}
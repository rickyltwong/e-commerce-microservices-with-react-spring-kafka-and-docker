package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.model.OrderMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class OrderConsumer {

    @Bean
    public Consumer<OrderMessage> consumer() {
        return orderMessage -> System.out.println("Received message: " + orderMessage);
    }
}

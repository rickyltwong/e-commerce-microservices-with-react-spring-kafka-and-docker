package com.ecommerce.orderservice.config;

//import com.ecommerce.orderservice.model.OrderMessage;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import java.sql.Timestamp;
//import java.util.function.Supplier;

@Configuration
public class OrderProducerConfig {

    // by default, the Supplier function in Spring Cloud Stream is invoked by a polling mechanism that calls it every second
//    @Bean
//    public Supplier<OrderMessage> producer() {
//        return () -> new OrderMessage("Order created", "Order message from Supplier" + new Timestamp(System.currentTimeMillis()));
//    }

//    spring:
//    cloud:
//    stream:
//    poller:
//    fixed-delay: 5000 // 5 seconds
}

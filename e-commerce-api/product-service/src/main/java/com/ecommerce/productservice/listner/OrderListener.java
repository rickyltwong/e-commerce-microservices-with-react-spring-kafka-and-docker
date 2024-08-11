package com.ecommerce.productservice.listner;

import com.ecommerce.productservice.dto.OrderRequestDTO;
import com.ecommerce.productservice.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class OrderListener {

    private final ProductService productService;

    public OrderListener(ProductService productService) {
        this.productService = productService;
    }

    @Bean
    public Consumer<OrderRequestDTO> processOrderRequest() {
        return orderRequest -> {
            // Process the order request
            System.out.println(orderRequest.toString());
        };
    }
}

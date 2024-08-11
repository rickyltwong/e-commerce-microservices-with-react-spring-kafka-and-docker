package com.ecommerce.productservice.listner;

import com.ecommerce.productservice.dto.OrderRequestDTO;
import com.ecommerce.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Slf4j
public class OrderListener {

    private final ProductService productService;

    public OrderListener(ProductService productService) {
        this.productService = productService;
    }

    @Bean
    Function<OrderRequestDTO, Boolean> processOrderRequest() {
        return (OrderRequestDTO orderRequestDTO) -> {
            log.info("Receiving Order{}", orderRequestDTO.toString());
            productService.placeOrder(orderRequestDTO);
            return true;
        };
    }
}

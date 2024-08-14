package com.ecommerce.adminservice.client;

import com.ecommerce.adminservice.dto.OrderDTO;
import com.ecommerce.adminservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderClient {

    @GetMapping("/api/orders")
    List<OrderDTO> getOrders();
}

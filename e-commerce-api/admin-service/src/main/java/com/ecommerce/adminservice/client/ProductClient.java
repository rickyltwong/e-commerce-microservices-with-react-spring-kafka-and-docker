package com.ecommerce.adminservice.client;

import com.ecommerce.adminservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products")
    List<ProductDTO> getProducts();

    @GetMapping("/api/products/{id}")
    ProductDTO getProductsById(@PathVariable UUID id);

    @PostMapping("/api/products")
    ProductDTO createProduct(@RequestBody ProductDTO products);

    @PutMapping("/api/products/{id}")
    ProductDTO updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productsDetails);

    @DeleteMapping("/api/products/{id}")
    void deleteProduct(@PathVariable UUID id);
}

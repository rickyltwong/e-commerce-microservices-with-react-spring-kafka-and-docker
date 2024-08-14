package com.ecommerce.adminservice.client;

import com.ecommerce.adminservice.config.FeignConfiguration;
import com.ecommerce.adminservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "product-service", configuration = FeignConfiguration.class)
public interface ProductClient {

    @GetMapping("/api/products")
    List<ProductDTO> getProducts();

    @GetMapping("/api/products/{id}")
    ProductDTO getProductsById(@PathVariable UUID id);

    @PostMapping("/api/products")
    ProductDTO createProduct(@RequestBody ProductDTO products);

    @PostMapping(value = "/api/products/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> updateProductImage(@PathVariable("id") UUID id, @RequestPart("file") MultipartFile file);

    @PutMapping("/api/products/{id}")
    ProductDTO updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productsDetails);

    @DeleteMapping("/api/products/{id}")
    void deleteProduct(@PathVariable UUID id);
}

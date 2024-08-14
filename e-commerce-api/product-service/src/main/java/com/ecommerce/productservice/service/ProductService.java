package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.OrderRequestDTO;
import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<ProductDTO> findAllProductsWithQuantity();

    Optional<ProductDTO> findProductByIdWithQuantity(UUID id);

    List<Product> findAll();

    Optional<Product> findById(UUID id);

    Product save(Product product);

    void deleteById(UUID id);

    void placeOrder(OrderRequestDTO orderRequestDTO);

    Product updateImage(MultipartFile image, UUID productId);
}

package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.entity.Products;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<ProductDTO> findAllProductsWithQuantity();

    Optional<ProductDTO> findProductByIdWithQuantity(UUID id);

    List<Products> findAll();

    Optional<Products> findById(UUID id);

    Products save(Products products);

    void deleteById(UUID id);
}

package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.entity.Products;
import com.ecommerce.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> findAllProductsWithQuantity() {
        return productRepository.findAllProductsWithQuantity();
    }

    @Override
    public Optional<ProductDTO> findProductByIdWithQuantity(UUID id) {
        return productRepository.findProductWithQuantityById(id);
    }

    @Override
    public List<Products> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Products> findById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Products save(Products products) {
        return productRepository.save(products);
    }

    @Override
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }
}

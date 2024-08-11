package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.OrderRequestDTO;
import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.entity.Inventory;
import com.ecommerce.productservice.entity.Products;
import com.ecommerce.productservice.repository.InventoryRepository;
import com.ecommerce.productservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final InventoryRepository inventoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
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

    @Override
    @Transactional
    public void placeOrder(OrderRequestDTO orderRequestDTO) {
        List<OrderRequestDTO.OrderItemRequest> orderItems = orderRequestDTO.getOrderItems();

        for (OrderRequestDTO.OrderItemRequest item : orderItems) {
            UUID productId = item.getProductId();
            int orderedQuantity = item.getQuantity();

            Inventory inventory = inventoryRepository.findByProductId(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found in inventory"));

            if (inventory.getQuantity() < orderedQuantity) {
                throw new RuntimeException("Insufficient inventory for product: " + productId);
            }

            inventory.setQuantity(inventory.getQuantity() - orderedQuantity);
            inventoryRepository.save(inventory);

            Products product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            productRepository.save(product);
        }
    }
}

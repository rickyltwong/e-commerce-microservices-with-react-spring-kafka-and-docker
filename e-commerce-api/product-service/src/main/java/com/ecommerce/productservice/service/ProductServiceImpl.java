package com.ecommerce.productservice.service;

import com.ecommerce.productservice.client.ImageClient;
import com.ecommerce.productservice.dto.OrderRequestDTO;
import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.entity.Inventory;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repository.InventoryRepository;
import com.ecommerce.productservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final InventoryRepository inventoryRepository;

    private final ImageClient imageClient;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, InventoryRepository inventoryRepository, ImageClient imageClient) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.imageClient = imageClient;
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
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
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

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            productRepository.save(product);
        }
    }

    @Override
    public Product updateImage(MultipartFile file, UUID id) {
        Product product = null;
        Optional<Product> productRes = productRepository.findById(id);
        if (productRes.isPresent()) {
            product = productRes.get();
            String imagePath = imageClient.uploadImage(file).getBody();
            product.setImagePath("/api/images/" + imagePath);
            product = productRepository.save(product);
        }
        return product;
    }
}

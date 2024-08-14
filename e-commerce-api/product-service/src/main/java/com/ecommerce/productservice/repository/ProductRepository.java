package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("""
            SELECT new com.ecommerce.productservice.dto.ProductDTO(p.productId, p.name, p.description, p.price, p.category, p.imagePath, COALESCE(i.quantity, 0)) \
            FROM Product p LEFT JOIN Inventory i ON p.productId = i.productId""")
    List<ProductDTO> findAllProductsWithQuantity();

    @Query("""
            SELECT new com.ecommerce.productservice.dto.ProductDTO(p.productId, p.name, p.description, p.price, p.category, p.imagePath, COALESCE(i.quantity, 0)) 
            FROM Product p LEFT JOIN Inventory i ON p.productId = i.productId
            WHERE p.productId = :productId""")
    Optional<ProductDTO> findProductWithQuantityById(UUID productId);
}


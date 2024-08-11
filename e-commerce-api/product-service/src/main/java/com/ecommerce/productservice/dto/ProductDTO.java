package com.ecommerce.productservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private UUID productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imagePath;
    private Integer quantity;

}
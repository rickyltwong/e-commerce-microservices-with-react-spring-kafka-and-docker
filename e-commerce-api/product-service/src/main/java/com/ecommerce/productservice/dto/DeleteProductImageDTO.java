package com.ecommerce.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductImageDTO {
    private String imageName;
    private UUID productId;
}

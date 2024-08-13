package com.ecommerce.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class InventoryDTO {
    private UUID inventoryId;
    private UUID productId;
    private Integer quantity;
}

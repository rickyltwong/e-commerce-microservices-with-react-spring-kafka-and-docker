package com.ecommerce.productservice.service;

import com.ecommerce.productservice.entity.Inventory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryService {
    List<Inventory> findAllInventories();

    Optional<Inventory> findInventoryById(UUID inventoryId);

    Inventory createInventory(Inventory inventory);

    Inventory updateInventory(UUID inventoryId, Inventory inventoryDetails);

    void deleteInventory(UUID inventoryId);
}
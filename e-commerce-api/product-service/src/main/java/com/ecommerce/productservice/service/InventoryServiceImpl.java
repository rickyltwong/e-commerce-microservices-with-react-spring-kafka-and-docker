package com.ecommerce.productservice.service;

import com.ecommerce.productservice.entity.Inventory;
import com.ecommerce.productservice.repository.InventoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<Inventory> findAllInventories() {
        return inventoryRepository.findAll();
    }

    @Override
    public Optional<Inventory> findInventoryById(UUID inventoryId) {
        return inventoryRepository.findById(inventoryId);
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateInventory(UUID inventoryId, Inventory inventoryDetails) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        inventory.setProductId(inventoryDetails.getProductId());
        inventory.setQuantity(inventoryDetails.getQuantity());

        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteInventory(UUID inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventoryRepository.delete(inventory);
    }
}

package com.ecommerce.adminservice.client;

import com.ecommerce.adminservice.dto.InventoryDTO;
import com.ecommerce.adminservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "product-service")
public interface InventoryClient {

    @GetMapping("/api/inventory")
    List<InventoryDTO> getProductInventories();

    @PostMapping("/api/inventory")
    InventoryDTO createInventory(@RequestBody InventoryDTO inventory);

    @PutMapping("/api/inventory/{id}")
    InventoryDTO updateInventory(@PathVariable UUID id, @RequestBody InventoryDTO inventoryDetails);

    @DeleteMapping("/api/inventory/{id}")
    void deleteInventory(@PathVariable UUID id);
}

package com.ecommerce.adminservice.controller;

import com.ecommerce.adminservice.dto.InventoryDTO;
import com.ecommerce.adminservice.dto.OrderDTO;
import com.ecommerce.adminservice.dto.ProductDTO;
import com.ecommerce.adminservice.service.AdminService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> products = adminService.getProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO products) {
        ProductDTO createdProduct = adminService.createProduct(products);
        return ResponseEntity.ok(createdProduct);
    }

    @PostMapping(value = "/products/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        adminService.uploadProductImage(id, file);
        return ResponseEntity.ok("Product image uploaded successfully");
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productsDetails) {
        ProductDTO updatedProduct = adminService.updateProduct(id, productsDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        adminService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<InventoryDTO>> getProductsInventory() {
        return ResponseEntity.ok(adminService.getInventory());
    }

    @PostMapping("/inventory")
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventory) {
        InventoryDTO createdInventory = adminService.createInventory(inventory);
        return ResponseEntity.ok(createdInventory);
    }

    @PutMapping("/inventory/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable UUID id, @RequestBody InventoryDTO inventoryDetails) {
        InventoryDTO updatedInventory = adminService.updateInventory(id, inventoryDetails);
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/inventory/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable UUID id) {
        adminService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = adminService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}

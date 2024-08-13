package com.ecommerce.adminservice.service;

import com.ecommerce.adminservice.client.InventoryClient;
import com.ecommerce.adminservice.client.ProductClient;
import com.ecommerce.adminservice.dto.InventoryDTO;
import com.ecommerce.adminservice.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {

    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    public AdminServiceImpl(ProductClient productClient, InventoryClient inventoryClient) {
        this.productClient = productClient;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public List<ProductDTO> getProducts() {
        return productClient.getProducts();
    }

    @Override
    public ProductDTO createProduct(ProductDTO products) {
        return productClient.createProduct(products);
    }

    @Override
    public ProductDTO updateProduct(UUID id, ProductDTO productsDetails) {
        return productClient.updateProduct(id, productsDetails);
    }

    @Override
    public void deleteProduct(UUID id) {
        productClient.deleteProduct(id);
    }

    @Override
    public List<InventoryDTO> getInventory() {
        return inventoryClient.getProductInventories();
    }

    @Override
    public InventoryDTO createInventory(InventoryDTO inventory) {
        return inventoryClient.createInventory(inventory);
    }

    @Override
    public InventoryDTO updateInventory(UUID id, InventoryDTO inventoryDetails) {
        return inventoryClient.updateInventory(id, inventoryDetails);
    }

    @Override
    public void deleteInventory(UUID id) {
        inventoryClient.deleteInventory(id);
    }
}

package com.ecommerce.adminservice.service;

import com.ecommerce.adminservice.dto.InventoryDTO;
import com.ecommerce.adminservice.dto.OrderDTO;
import com.ecommerce.adminservice.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    List<ProductDTO> getProducts();

    ProductDTO getProductById(UUID id);

    ProductDTO createProduct(ProductDTO products);

    ProductDTO updateProduct(UUID id, ProductDTO productsDetails);

    void deleteProduct(UUID id);

    List<InventoryDTO> getInventory();

    InventoryDTO createInventory(InventoryDTO inventory);

    InventoryDTO updateInventory(UUID id, InventoryDTO inventoryDetails);

    void deleteInventory(UUID id);

    List<OrderDTO> getAllOrders();
}

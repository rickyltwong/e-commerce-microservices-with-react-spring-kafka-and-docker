package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.dto.ProductResponse;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    // private final ProductClient productClient;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository /*, ProductClient productClient */) {
        this.orderRepository = orderRepository;
        // this.productClient = productClient;
    }

    // NOTE: This method is commented out since it is not used in the current implementation
//    public Order createOrder(List<OrderItem> orderItems) {
//        Order order = new Order();
//        orderItems.forEach(order::addOrderItem);
//        return orderRepository.save(order);
//    }

    public Order placeOrder(List<OrderRequest.OrderItemRequest> orderItemRequests) {

        Order order = new Order();

        List<OrderItem> orderItems = orderItemRequests.stream()
                .map(item -> {
                    // Mocking the product response since ProductService is not ready
                    // ProductResponse productResponse = productClient.getProductById(item.getProductId());
                    ProductResponse productResponse = mockProductResponse(item.getProductId());

                    return new OrderItem(
                            null,
                            order,
                            productResponse.getProductId(),
                            item.getQuantity()
                    );
                })
                .toList();

        BigDecimal totalAmount = orderItems.stream()
                .map(orderItem -> {
                    // Assuming the mockProductResponse method provides the product price
                    ProductResponse productResponse = mockProductResponse(orderItem.getProductId());
                    return productResponse.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);
        orderItems.forEach(order::addOrderItem);

        return orderRepository.save(order);
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Mocking method to simulate product service response
    private ProductResponse mockProductResponse(UUID productId) {
        // Mocking a fixed product response for demonstration
        return new ProductResponse(
                productId,
                "Mock Product",
                "This is a mock product description.",
                new BigDecimal("19.99"),
                "Mock Category",
                "mock_image_path.jpg"
        );
    }
}

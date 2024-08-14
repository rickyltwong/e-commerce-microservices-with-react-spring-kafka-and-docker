package com.ecommerce.adminservice.service;

import com.ecommerce.adminservice.dto.DeleteProductImageDTO;
import com.ecommerce.adminservice.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class DeleteImageEventPublisher {

    private final StreamBridge streamBridge;

    @Autowired
    public DeleteImageEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishDeleteProductImage(ProductDTO productDTO) {
        DeleteProductImageDTO deleteProductImageEvent = new DeleteProductImageDTO();
        String filename = productDTO.getImagePath().substring(productDTO.getImagePath().lastIndexOf('/') + 1);
        deleteProductImageEvent.setImageName(filename);
        deleteProductImageEvent.setProductId(productDTO.getProductId());

        streamBridge.send("deleteProductImage-out-0", deleteProductImageEvent);
    }
}

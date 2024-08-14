package com.ecommerce.productservice.listner;

import com.ecommerce.productservice.client.ImageClient;
import com.ecommerce.productservice.dto.DeleteProductImageDTO;
import com.ecommerce.productservice.dto.OrderRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Slf4j
public class DeleteProductImageListener {

    private final ImageClient imageService;

    public DeleteProductImageListener(ImageClient imageService) {
        this.imageService = imageService;
    }

    @Bean
    Function<DeleteProductImageDTO, Boolean> processDeleteImageRequest() {
        return (DeleteProductImageDTO imgDeleteDTO) -> {
            log.info("Receiving image delete {}", imgDeleteDTO.toString());
            imageService.deleteImage(imgDeleteDTO.getImageName());
            return true;
        };
    }
}

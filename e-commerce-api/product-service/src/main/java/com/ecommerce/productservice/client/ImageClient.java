package com.ecommerce.productservice.client;

import com.ecommerce.productservice.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(name = "image-service", configuration = FeignConfiguration.class)
public interface ImageClient {

    @PostMapping(value = "/api/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadImage(@RequestPart("file") MultipartFile file);

    @DeleteMapping(value = "/api/images/{fileName}")
    ResponseEntity<Void> deleteImage(@PathVariable("fileName") String fileName);

}

package com.ecommerce.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(name = "image-service")
public interface ImageClient {

    @PostMapping(value = "/api/images", consumes = "multipart/form-data")
    ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file);
}

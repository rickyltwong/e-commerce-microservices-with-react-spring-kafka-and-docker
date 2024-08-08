package com.ecommerce.imageservice.controller;

import com.ecommerce.imageservice.service.ImageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api/images")
public class ImageController {


    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String fileName = imageService.uploadImage(file);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<InputStream> downloadImage(@PathVariable String fileName) {
        InputStream inputStream = imageService.downloadImage(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(inputStream);
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<Void> deleteImage(@PathVariable String fileName) {
        imageService.deleteImage(fileName);
        return ResponseEntity.noContent().build();
    }
}

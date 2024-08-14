package com.ecommerce.imageservice.controller;

import com.ecommerce.imageservice.service.ImageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Image service /api/images/test";
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String fileName = imageService.uploadImage(file);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable String fileName) {
        try {
            InputStream imageStream = imageService.downloadImage(fileName);

            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            MediaType mediaType = getMediaTypeForFileName(fileExtension);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .contentType(mediaType)
                    .body(new InputStreamResource(imageStream));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    private MediaType getMediaTypeForFileName(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }


    @DeleteMapping("/{fileName}")
    public ResponseEntity<Void> deleteImage(@PathVariable String fileName) {
        imageService.deleteImage(fileName);
        return ResponseEntity.noContent().build();
    }
}

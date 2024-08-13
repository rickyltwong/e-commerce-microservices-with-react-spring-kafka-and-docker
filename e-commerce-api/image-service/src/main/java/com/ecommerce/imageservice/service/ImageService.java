package com.ecommerce.imageservice.service;

import io.minio.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @PostConstruct
    public void init() {
        createBucketIfNotExists();
        uploadInitialImages();
    }

    private void createBucketIfNotExists() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("Bucket '" + bucketName + "' created.");
            } else {
                System.out.println("Bucket '" + bucketName + "' already exists.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error checking or creating bucket in MinIO", e);
        }
    }

    public void uploadInitialImages() {
        try {
            String[] imagePaths = {
                    "ipad.jpg",
                    "ipadpro.jpg",
                    "iphone12.jpg",
                    "iphone13.jpg",
                    "iphonese.jpg",
                    "macbook.jpg"
            };

            for (String imagePath : imagePaths) {
                uploadResourceImage(imagePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error uploading initial images to MinIO", e);
        }
    }

    private void uploadResourceImage(String resourcePath) {
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);
            InputStream inputStream = resource.getInputStream();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(resourcePath)
                            .stream(inputStream, resource.contentLength(), -1)
                            .contentType("image/jpeg")
                            .build()
            );

            System.out.println("Uploaded image: " + resourcePath);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file " + resourcePath + " to MinIO", e);
        }
    }


    public String uploadImage(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();

            String contentType = file.getContentType();

            System.out.println("Uploading file with content type: " + contentType);

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(contentType)
                            .build()
            );

            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to MinIO", e);
        }
    }


    public InputStream downloadImage(String fileName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            throw new RuntimeException("Error downloading file from MinIO", e);
        }
    }

    public void deleteImage(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting file from MinIO", e);
        }
    }
}

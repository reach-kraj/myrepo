package com.pilgrim.service.impl;

import com.pilgrim.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Override
    public String uploadImage(MultipartFile file, String folder) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Validate file type
        String contentType = file.getContentType();
        if (!isValidImageType(contentType)) {
            throw new IllegalArgumentException("Invalid file type. Only JPEG, PNG, and WebP are allowed.");
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + fileExtension;
        String key = folder + uniqueFilename;

        try {
            // Upload to S3 with public read access
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(contentType)
                    .contentLength(file.getSize())
                    .acl(ObjectCannedACL.PUBLIC_READ) // Make the object publicly readable
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            // Generate and return the public URL
            String imageUrl = generateImageUrl(bucketName, key);
            log.info("Image uploaded successfully with public access: {}", imageUrl);
            return imageUrl;

        } catch (Exception e) {
            log.error("Error uploading image to S3: {}", e.getMessage());
            throw new IOException("Failed to upload image to S3", e);
        }
    }

    @Override
    public List<String> uploadMultipleImages(List<MultipartFile> files, String folder) throws IOException {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String imageUrl = uploadImage(file, folder);
                imageUrls.add(imageUrl);
            }
        }

        return imageUrls;
    }

    @Override
    public void deleteImage(String imageUrl) {
        try {
            // Extract key from URL
            String key = extractKeyFromUrl(imageUrl);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            log.info("Image deleted successfully: {}", imageUrl);

        } catch (Exception e) {
            log.error("Error deleting image from S3: {}", e.getMessage());
            // Don't throw exception for delete failures
        }
    }

    @Override
    public String generateImageUrl(String bucketName, String key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
    }

    private boolean isValidImageType(String contentType) {
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/jpg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/webp")
        );
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return ".jpg"; // Default extension
        }
        return filename.substring(filename.lastIndexOf('.'));
    }

    private String extractKeyFromUrl(String imageUrl) {
        // Extract key from S3 URL format: https://bucket.s3.region.amazonaws.com/key
        String baseUrl = String.format("https://%s.s3.%s.amazonaws.com/", bucketName, region);
        if (imageUrl.startsWith(baseUrl)) {
            return imageUrl.substring(baseUrl.length());
        }
        return imageUrl; // Return as-is if URL format is different
    }
}
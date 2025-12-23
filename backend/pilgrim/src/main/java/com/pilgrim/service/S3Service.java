package com.pilgrim.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface S3Service {
    String uploadImage(MultipartFile file, String folder) throws IOException;
    List<String> uploadMultipleImages(List<MultipartFile> files, String folder) throws IOException;
    void deleteImage(String imageUrl);
    String generateImageUrl(String bucketName, String key);
}
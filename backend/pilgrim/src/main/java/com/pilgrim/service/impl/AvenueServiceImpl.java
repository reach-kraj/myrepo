// AvenueServiceImpl.java
package com.pilgrim.service.impl;

import com.pilgrim.entity.Avenue;
import com.pilgrim.entity.AvenueImage;
import com.pilgrim.entity.User;
import com.pilgrim.mapper.AvenueRequest;
import com.pilgrim.mapper.AvenueUpdateRequest;
import com.pilgrim.mapper.AvenueImageRequest;
import com.pilgrim.mapper.AvenueImageUpdateRequest;
import com.pilgrim.repository.AvenueRepository;
import com.pilgrim.repository.AvenueImageRepository;
import com.pilgrim.repository.UserRepository;
import com.pilgrim.service.AvenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import com.pilgrim.service.S3Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvenueServiceImpl implements AvenueService {

    private final AvenueRepository avenueRepository;
    private final AvenueImageRepository avenueImageRepository;
    private final S3Service s3Service; // Add this
    private final UserRepository userRepository;

    @Value("${aws.s3.avenue-folder}")
    private String avenueFolder;

    @Override
    @Transactional
    public Avenue createAvenue(AvenueRequest request) {
        // Create avenue
        Avenue avenue = new Avenue();
        avenue.setAvenueName(request.getAvenueName());
        avenue.setAvenueSpecial(request.getAvenueSpecial());
        avenue.setDistrict(request.getDistrict());
        avenue.setState(request.getState());
        avenue.setArea(request.getArea());
        avenue.setGpsLatitude(request.getGpsLatitude());
        avenue.setGpsLongitude(request.getGpsLongitude());
        avenue.setAvenueTimings(request.getAvenueTimings());
        avenue.setAvenueDetails(request.getAvenueDetails());
        avenue.setCreatedBy(request.getCreatedBy());
        avenue.setIsActive(true);

        Avenue savedAvenue = avenueRepository.save(avenue);

        // Add images if provided
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            for (AvenueImageRequest imageRequest : request.getImages()) {
                addAvenueImage(savedAvenue.getAvenueId(),
                        imageRequest.getImageUrl(),
                        imageRequest.getCaption(),
                        request.getCreatedBy());
            }
        }

        return savedAvenue;
    }

    @Override
    @Transactional
    public Avenue updateAvenue(AvenueUpdateRequest request) {
        Avenue avenue = avenueRepository.findByAvenueIdAndIsActiveTrue(request.getAvenueId())
                .orElseThrow(() -> new RuntimeException("Avenue not found"));

        // Update avenue fields
        avenue.setAvenueName(request.getAvenueName());
        avenue.setAvenueSpecial(request.getAvenueSpecial());
        avenue.setDistrict(request.getDistrict());
        avenue.setState(request.getState());
        avenue.setArea(request.getArea());
        avenue.setGpsLatitude(request.getGpsLatitude());
        avenue.setGpsLongitude(request.getGpsLongitude());
        avenue.setAvenueTimings(request.getAvenueTimings());
        avenue.setAvenueDetails(request.getAvenueDetails());
        avenue.setUpdatedBy(request.getUpdatedBy());

        Avenue savedAvenue = avenueRepository.save(avenue);

        // Handle image operations
        if (request.getDeleteImageIds() != null) {
            for (Integer imageId : request.getDeleteImageIds()) {
                deleteAvenueImage(imageId, request.getUpdatedBy());
            }
        }

        if (request.getNewImages() != null) {
            for (AvenueImageRequest imageRequest : request.getNewImages()) {
                addAvenueImage(savedAvenue.getAvenueId(),
                        imageRequest.getImageUrl(),
                        imageRequest.getCaption(),
                        request.getUpdatedBy());
            }
        }

        if (request.getUpdateImages() != null) {
            for (AvenueImageUpdateRequest updateRequest : request.getUpdateImages()) {
                updateAvenueImageCaption(updateRequest.getImageId(),
                        updateRequest.getCaption(),
                        request.getUpdatedBy());
            }
        }

        return savedAvenue;
    }

    @Override
    public Avenue getAvenueById(Integer avenueId) {
        return avenueRepository.findByAvenueIdAndIsActiveTrue(avenueId)
                .orElseThrow(() -> new RuntimeException("Avenue not found"));
    }

    @Override
    @Transactional
    public void deleteAvenue(Integer avenueId, String deletedBy) {
        Avenue avenue = avenueRepository.findByAvenueIdAndIsActiveTrue(avenueId)
                .orElseThrow(() -> new RuntimeException("Avenue not found"));

        avenue.setIsActive(false);
        avenue.setDeletedAt(LocalDateTime.now());
        avenue.setDeletedBy(deletedBy);

        avenueRepository.save(avenue);

        // Soft delete associated images
        List<AvenueImage> images = avenueImageRepository.findByAvenueIdAndIsActiveTrueOrderByCreatedAtAsc(avenueId);
        for (AvenueImage image : images) {
            image.setIsActive(false);
            image.setDeletedAt(LocalDateTime.now());
            image.setDeletedBy(deletedBy);
            avenueImageRepository.save(image);
        }
    }

    @Override
    public Page<Avenue> getAllAvenues(Pageable pageable) {
        return avenueRepository.findByIsActiveTrue(pageable);
    }

    @Override
    public List<Avenue> getAllAvenuesSimple() {
        return avenueRepository.findByIsActiveTrueOrderByCreatedAtDesc();
    }

    @Override
    public Page<Avenue> searchAvenues(String avenueName, String avenueSpecial,
                                      String district, String state, String area, Pageable pageable) {
        if (avenueName != null && !avenueName.trim().isEmpty()) {
            return avenueRepository.findByAvenueNameContainingIgnoreCaseAndIsActiveTrue(avenueName, pageable);
        }
        if (avenueSpecial != null && !avenueSpecial.trim().isEmpty()) {
            return avenueRepository.findByAvenueSpecialContainingIgnoreCaseAndIsActiveTrue(avenueSpecial, pageable);
        }
        if (district != null && !district.trim().isEmpty()) {
            return avenueRepository.findByDistrictContainingIgnoreCaseAndIsActiveTrue(district, pageable);
        }
        if (state != null && !state.trim().isEmpty()) {
            return avenueRepository.findByStateContainingIgnoreCaseAndIsActiveTrue(state, pageable);
        }
        if (area != null && !area.trim().isEmpty()) {
            return avenueRepository.findByAreaContainingIgnoreCaseAndIsActiveTrue(area, pageable);
        }

        return avenueRepository.findByIsActiveTrue(pageable);
    }

    @Override
    public Page<Avenue> searchByLocation(String state, String district, String area, Pageable pageable) {
        return avenueRepository.findByLocationFilters(state, district, area, pageable);
    }

    @Override
    public List<Avenue> findNearbyAvenues(Double latitude, Double longitude, Double radiusKm) {
        return avenueRepository.findNearbyAvenues(latitude, longitude, radiusKm);
    }

    @Override
    public List<AvenueImage> getAvenueImages(Integer avenueId) {
        return avenueImageRepository.findByAvenueIdAndIsActiveTrueOrderByCreatedAtAsc(avenueId);
    }

    @Override
    @Transactional
    public AvenueImage addAvenueImage(Integer avenueId, String imageUrl, String caption, String createdBy) {
        // Verify avenue exists
        avenueRepository.findByAvenueIdAndIsActiveTrue(avenueId)
                .orElseThrow(() -> new RuntimeException("Avenue not found"));

        AvenueImage image = new AvenueImage();
        image.setAvenueId(avenueId);
        image.setImageUrl(imageUrl);
        image.setCaption(caption);
        image.setCreatedBy(createdBy);
        image.setIsActive(true);

        return avenueImageRepository.save(image);
    }


    @Override
    @Transactional
    public AvenueImage updateAvenueImageCaption(Integer imageId, String caption, String updatedBy) {
        AvenueImage image = avenueImageRepository.findByImageIdAndIsActiveTrue(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        image.setCaption(caption);
        image.setUpdatedBy(updatedBy);

        return avenueImageRepository.save(image);
    }
    @Override
    @Transactional
    public AvenueImage uploadAndAddImage(Integer avenueId, MultipartFile file, String caption, String createdBy) throws IOException {
        // Verify avenue exists
        avenueRepository.findByAvenueIdAndIsActiveTrue(avenueId)
                .orElseThrow(() -> new RuntimeException("Avenue not found"));

        // Upload image to S3
        String imageUrl = s3Service.uploadImage(file, avenueFolder);

        // Save image record to database
        AvenueImage image = new AvenueImage();
        image.setAvenueId(avenueId);
        image.setImageUrl(imageUrl);
        image.setCaption(caption);
        image.setCreatedBy(createdBy);
        image.setIsActive(true);

        return avenueImageRepository.save(image);
    }

    @Override
    @Transactional
    public List<AvenueImage> uploadAndAddMultipleImages(Integer avenueId, List<MultipartFile> files, List<String> captions, String createdBy) throws IOException {
        // Verify avenue exists
        avenueRepository.findByAvenueIdAndIsActiveTrue(avenueId)
                .orElseThrow(() -> new RuntimeException("Avenue not found"));

        List<AvenueImage> savedImages = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String caption = (captions != null && i < captions.size()) ? captions.get(i) : null;

            if (!file.isEmpty()) {
                AvenueImage savedImage = uploadAndAddImage(avenueId, file, caption, createdBy);
                savedImages.add(savedImage);
            }
        }

        return savedImages;
    }

    @Override
    @Transactional
    public void deleteAvenueImage(Integer imageId, String deletedBy) {
        AvenueImage image = avenueImageRepository.findByImageIdAndIsActiveTrue(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Delete from S3
        s3Service.deleteImage(image.getImageUrl());

        // Soft delete from database
        image.setIsActive(false);
        image.setDeletedAt(LocalDateTime.now());
        image.setDeletedBy(deletedBy);

        avenueImageRepository.save(image);
    }
    @Override
    public Page<Avenue> unifiedSearch(String query, String searchType, Pageable pageable) {
        if (query == null || query.trim().isEmpty()) {
            return getAllAvenues(pageable); // Return all if no query
        }

        String trimmedQuery = query.trim();

        if (searchType == null || searchType.isEmpty()) {
            searchType = "all"; // Default to search all fields
        }

        switch (searchType.toLowerCase()) {
            case "name":
                return avenueRepository.findByAvenueNameContainingIgnoreCase(trimmedQuery, pageable);
            case "location":
                return avenueRepository.findByLocationSearch(trimmedQuery, pageable);
            case "special":
                return avenueRepository.findByAvenueSpecialContainingIgnoreCase(trimmedQuery, pageable);
            case "all":
            default:
                return avenueRepository.findByMultiFieldSearch(trimmedQuery, pageable);
        }
    }
    // NEW METHOD: Get avenue with user details
    public Map<String, Object> getAvenueWithUserDetails(Integer avenueId) {
        // Get the avenue details
        Avenue avenue = avenueRepository.findByAvenueIdAndIsActiveTrue(avenueId)
                .orElseThrow(() -> new RuntimeException("Avenue not found"));

        // Get avenue images
        List<AvenueImage> images = avenueImageRepository.findByAvenueIdAndIsActiveTrueOrderByCreatedAtAsc(avenueId);

        // Build response with user names
        Map<String, Object> response = buildAvenueResponseWithUserNames(avenue, images);

        return response;
    }

    private Map<String, Object> buildAvenueResponseWithUserNames(Avenue avenue, List<AvenueImage> images) {
        Map<String, Object> avenueData = new HashMap<>();

        // Basic avenue data
        avenueData.put("avenueId", avenue.getAvenueId());
        avenueData.put("avenueName", avenue.getAvenueName());
        avenueData.put("avenueSpecial", avenue.getAvenueSpecial());
        avenueData.put("district", avenue.getDistrict());
        avenueData.put("state", avenue.getState());
        avenueData.put("area", avenue.getArea());
        avenueData.put("gpsLatitude", avenue.getGpsLatitude());
        avenueData.put("gpsLongitude", avenue.getGpsLongitude());
        avenueData.put("avenueTimings", avenue.getAvenueTimings());
        avenueData.put("avenueDetails", avenue.getAvenueDetails());
        avenueData.put("isActive", avenue.getIsActive());

        // Timestamps
        avenueData.put("createdAt", avenue.getCreatedAt());
        avenueData.put("updatedAt", avenue.getUpdatedAt());

        // Original user IDs (for reference)
        avenueData.put("createdBy", avenue.getCreatedBy());
        avenueData.put("updatedBy", avenue.getUpdatedBy());

        // Add user names
        avenueData.put("createdByName", getUserFullName(avenue.getCreatedBy()));
        avenueData.put("updatedByName", getUserFullName(avenue.getUpdatedBy()));

        // Include images with creator names
        if (images != null && !images.isEmpty()) {
            List<Map<String, Object>> imagesWithUserNames = images.stream()
                    .map(this::buildImageResponseWithUserName)
                    .collect(Collectors.toList());
            avenueData.put("images", imagesWithUserNames);
        } else {
            avenueData.put("images", new ArrayList<>());
        }

        return avenueData;
    }

    private Map<String, Object> buildImageResponseWithUserName(AvenueImage image) {
        Map<String, Object> imageData = new HashMap<>();
        imageData.put("imageId", image.getImageId());
        imageData.put("avenueId", image.getAvenueId());
        imageData.put("imageUrl", image.getImageUrl());
        imageData.put("caption", image.getCaption());
        imageData.put("createdAt", image.getCreatedAt());
        imageData.put("createdBy", image.getCreatedBy());
        imageData.put("createdByName", getUserFullName(image.getCreatedBy()));
        return imageData;
    }

    private String getUserFullName(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return "N/A";
        }

        try {
            Optional<User> userOpt = userRepository.findByUserIdAndIsActiveTrue(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                String firstName = user.getFirstname() != null ? user.getFirstname() : "";
                String lastName = user.getLastname() != null ? user.getLastname() : "";
                String fullName = (firstName + " " + lastName).trim();
                return fullName.isEmpty() ? "N/A" : fullName;
            }
            return "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }
// NEW METHODS for deleted avenues management

    @Override
    public Page<Avenue> getAllDeletedAvenues(Pageable pageable) {
        return avenueRepository.findByIsActiveFalseAndDeletedAtIsNotNull(pageable);
    }

    @Override
    public List<Avenue> getAllDeletedAvenuesSimple() {
        return avenueRepository.findByIsActiveFalseAndDeletedAtIsNotNullOrderByDeletedAtDesc();
    }

    @Override
    public Page<Avenue> searchDeletedAvenues(String avenueName, String avenueSpecial,
                                             String district, String state, String area, Pageable pageable) {
        // Search within deleted avenues only
        if (avenueName != null && !avenueName.trim().isEmpty()) {
            return avenueRepository.findByAvenueNameContainingIgnoreCaseAndIsActiveFalseAndDeletedAtIsNotNull(avenueName, pageable);
        }
        if (avenueSpecial != null && !avenueSpecial.trim().isEmpty()) {
            return avenueRepository.findByAvenueSpecialContainingIgnoreCaseAndIsActiveFalseAndDeletedAtIsNotNull(avenueSpecial, pageable);
        }
        if (district != null && !district.trim().isEmpty()) {
            return avenueRepository.findByDistrictContainingIgnoreCaseAndIsActiveFalseAndDeletedAtIsNotNull(district, pageable);
        }
        if (state != null && !state.trim().isEmpty()) {
            return avenueRepository.findByStateContainingIgnoreCaseAndIsActiveFalseAndDeletedAtIsNotNull(state, pageable);
        }
        if (area != null && !area.trim().isEmpty()) {
            return avenueRepository.findByAreaContainingIgnoreCaseAndIsActiveFalseAndDeletedAtIsNotNull(area, pageable);
        }

        // Default: return all deleted avenues
        return avenueRepository.findByIsActiveFalseAndDeletedAtIsNotNull(pageable);
    }

    @Override
    public Map<String, Object> getDeletedAvenueWithUserDetails(Integer avenueId) {
        // Get the deleted avenue details
        Avenue avenue = avenueRepository.findByAvenueIdAndIsActiveFalseAndDeletedAtIsNotNull(avenueId)
                .orElseThrow(() -> new RuntimeException("Deleted avenue not found"));

        // Get deleted avenue images (including deleted ones)
        List<AvenueImage> images = avenueImageRepository.findByAvenueIdOrderByCreatedAtAsc(avenueId);

        // Build response with user names (including deletedBy information)
        Map<String, Object> response = buildDeletedAvenueResponseWithUserNames(avenue, images);

        return response;
    }

    @Override
    @Transactional
    public Avenue restoreAvenue(Integer avenueId, String restoredBy) {
        Avenue avenue = avenueRepository.findByAvenueIdAndIsActiveFalseAndDeletedAtIsNotNull(avenueId)
                .orElseThrow(() -> new RuntimeException("Deleted avenue not found"));

        // Restore the avenue
        avenue.setIsActive(true);
        avenue.setDeletedAt(null);
        avenue.setDeletedBy(null);
        avenue.setUpdatedBy(restoredBy);

        Avenue restoredAvenue = avenueRepository.save(avenue);

        // Optionally restore associated images
        List<AvenueImage> deletedImages = avenueImageRepository.findByAvenueIdAndIsActiveFalseAndDeletedAtIsNotNull(avenueId);
        for (AvenueImage image : deletedImages) {
            image.setIsActive(true);
            image.setDeletedAt(null);
            image.setDeletedBy(null);
            image.setUpdatedBy(restoredBy);
            avenueImageRepository.save(image);
        }

        return restoredAvenue;
    }

    // Helper method for building deleted avenue response with user details
    private Map<String, Object> buildDeletedAvenueResponseWithUserNames(Avenue avenue, List<AvenueImage> images) {
        Map<String, Object> avenueData = new HashMap<>();

        // Basic avenue data
        avenueData.put("avenueId", avenue.getAvenueId());
        avenueData.put("avenueName", avenue.getAvenueName());
        avenueData.put("avenueSpecial", avenue.getAvenueSpecial());
        avenueData.put("district", avenue.getDistrict());
        avenueData.put("state", avenue.getState());
        avenueData.put("area", avenue.getArea());
        avenueData.put("gpsLatitude", avenue.getGpsLatitude());
        avenueData.put("gpsLongitude", avenue.getGpsLongitude());
        avenueData.put("avenueTimings", avenue.getAvenueTimings());
        avenueData.put("avenueDetails", avenue.getAvenueDetails());
        avenueData.put("isActive", avenue.getIsActive());

        // Timestamps
        avenueData.put("createdAt", avenue.getCreatedAt());
        avenueData.put("updatedAt", avenue.getUpdatedAt());
        avenueData.put("deletedAt", avenue.getDeletedAt());

        // User IDs
        avenueData.put("createdBy", avenue.getCreatedBy());
        avenueData.put("updatedBy", avenue.getUpdatedBy());
        avenueData.put("deletedBy", avenue.getDeletedBy());

        // User names
        avenueData.put("createdByName", getUserFullName(avenue.getCreatedBy()));
        avenueData.put("updatedByName", getUserFullName(avenue.getUpdatedBy()));
        avenueData.put("deletedByName", getUserFullName(avenue.getDeletedBy()));

        // Include all images (both active and deleted)
        if (images != null && !images.isEmpty()) {
            List<Map<String, Object>> imagesWithUserNames = images.stream()
                    .map(this::buildDeletedImageResponseWithUserName)
                    .collect(Collectors.toList());
            avenueData.put("images", imagesWithUserNames);
        } else {
            avenueData.put("images", new ArrayList<>());
        }

        return avenueData;
    }

    private Map<String, Object> buildDeletedImageResponseWithUserName(AvenueImage image) {
        Map<String, Object> imageData = new HashMap<>();
        imageData.put("imageId", image.getImageId());
        imageData.put("avenueId", image.getAvenueId());
        imageData.put("imageUrl", image.getImageUrl());
        imageData.put("caption", image.getCaption());
        imageData.put("isActive", image.getIsActive());
        imageData.put("createdAt", image.getCreatedAt());
        imageData.put("updatedAt", image.getUpdatedAt());
        imageData.put("deletedAt", image.getDeletedAt());
        imageData.put("createdBy", image.getCreatedBy());
        imageData.put("updatedBy", image.getUpdatedBy());
        imageData.put("deletedBy", image.getDeletedBy());
        imageData.put("createdByName", getUserFullName(image.getCreatedBy()));
        imageData.put("updatedByName", getUserFullName(image.getUpdatedBy()));
        imageData.put("deletedByName", getUserFullName(image.getDeletedBy()));
        return imageData;
    }

}
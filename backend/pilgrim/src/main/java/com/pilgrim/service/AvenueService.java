// AvenueService.java
package com.pilgrim.service;

import com.pilgrim.entity.Avenue;
import com.pilgrim.entity.AvenueImage;
import com.pilgrim.mapper.AvenueRequest;
import com.pilgrim.mapper.AvenueUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AvenueService {

    // NEW METHOD: Get avenue with user details
    Map<String, Object> getAvenueWithUserDetails(Integer avenueId);
    // CRUD operations
    Avenue createAvenue(AvenueRequest request);
    Avenue updateAvenue(AvenueUpdateRequest request);
    Avenue getAvenueById(Integer avenueId);
    void deleteAvenue(Integer avenueId, String deletedBy);

    // List operations
    Page<Avenue> getAllAvenues(Pageable pageable);
    List<Avenue> getAllAvenuesSimple();

    // Search operations
    Page<Avenue> searchAvenues(String avenueName, String avenueSpecial,
                               String district, String state, String area, Pageable pageable);
    Page<Avenue> searchByLocation(String state, String district, String area, Pageable pageable);
    List<Avenue> findNearbyAvenues(Double latitude, Double longitude, Double radiusKm);

    // NEW: Unified search operation
    Page<Avenue> unifiedSearch(String query, String searchType, Pageable pageable);

    // Image operations
    List<AvenueImage> getAvenueImages(Integer avenueId);
    AvenueImage addAvenueImage(Integer avenueId, String imageUrl, String caption, String createdBy);
    void deleteAvenueImage(Integer imageId, String deletedBy);
    AvenueImage updateAvenueImageCaption(Integer imageId, String caption, String updatedBy);

    // Image upload operations
    AvenueImage uploadAndAddImage(Integer avenueId, MultipartFile file, String caption, String createdBy) throws IOException;
    List<AvenueImage> uploadAndAddMultipleImages(Integer avenueId, List<MultipartFile> files, List<String> captions, String createdBy) throws IOException;
    // NEW: Deleted avenues management
    Page<Avenue> getAllDeletedAvenues(Pageable pageable);
    List<Avenue> getAllDeletedAvenuesSimple();
    Page<Avenue> searchDeletedAvenues(String avenueName, String avenueSpecial,
                                      String district, String state, String area, Pageable pageable);
    Map<String, Object> getDeletedAvenueWithUserDetails(Integer avenueId);

    // Optional: Restore functionality
    Avenue restoreAvenue(Integer avenueId, String restoredBy);

}
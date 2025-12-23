// AvenueController.java
package com.pilgrim.controller;

import com.pilgrim.entity.Avenue;
import com.pilgrim.entity.AvenueImage;
import com.pilgrim.mapper.AvenueRequest;
import com.pilgrim.mapper.AvenueUpdateRequest;
import com.pilgrim.service.AvenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/avenues")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AvenueController {

    private final AvenueService avenueService;

    // =============== CREATE ENDPOINTS ===============

    @PostMapping("/admin/create")
    public ResponseEntity<Map<String, Object>> createAvenue(@Valid @RequestBody AvenueRequest request) {
        try {
            Avenue avenue = avenueService.createAvenue(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Avenue created successfully");
            response.put("data", buildAvenueResponse(avenue));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to create avenue: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // =============== UPDATE ENDPOINTS ===============

    @PutMapping("/admin/{avenueId}")
    public ResponseEntity<Map<String, Object>> updateAvenue(
            @PathVariable Integer avenueId,
            @Valid @RequestBody AvenueUpdateRequest request) {
        try {
            request.setAvenueId(avenueId);
            Avenue avenue = avenueService.updateAvenue(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Avenue updated successfully");
            response.put("data", buildAvenueResponse(avenue));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update avenue: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // =============== GET SINGLE AVENUE ENDPOINTS ===============
    @GetMapping("/admin/{avenueId}")
    public ResponseEntity<Map<String, Object>> getAvenueByIdForAdmin(@PathVariable Integer avenueId) {
        try {
            // Use the new method that includes user names
            Map<String, Object> avenueData = avenueService.getAvenueWithUserDetails(avenueId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Avenue retrieved successfully");
            response.put("data", avenueData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Avenue not found: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/mobile/{avenueId}")
    public ResponseEntity<Map<String, Object>> getAvenueByIdForMobile(@PathVariable Integer avenueId) {
        try {
            Avenue avenue = avenueService.getAvenueById(avenueId);
            List<AvenueImage> images = avenueService.getAvenueImages(avenueId);

            Map<String, Object> avenueData = buildMobileAvenueResponse(avenue);
            avenueData.put("images", buildImageResponses(images));

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Avenue retrieved successfully");
            response.put("data", avenueData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Avenue not found: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Generic endpoint (backward compatibility)
    @GetMapping("/{avenueId}")
    public ResponseEntity<Map<String, Object>> getAvenueById(@PathVariable Integer avenueId) {
        try {
            // Use the new method that includes user names
            Map<String, Object> avenueData = avenueService.getAvenueWithUserDetails(avenueId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Avenue retrieved successfully");
            response.put("data", avenueData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Avenue not found: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    // =============== DELETE ENDPOINTS ===============

    @DeleteMapping("/admin/{avenueId}")
    public ResponseEntity<Map<String, Object>> deleteAvenue(
            @PathVariable Integer avenueId,
            @RequestParam String deletedBy) {
        try {
            avenueService.deleteAvenue(avenueId, deletedBy);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Avenue deleted successfully");
            response.put("data", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to delete avenue: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    // =============== DELETED AVENUES ENDPOINTS ===============

    @GetMapping("/admin/deleted/all")
    public ResponseEntity<Map<String, Object>> getAllDeletedAvenuesForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "deletedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Avenue> avenuePage = avenueService.getAllDeletedAvenues(pageable);

            List<Map<String, Object>> avenues = avenuePage.getContent().stream()
                    .map(this::buildDeletedAvenueResponse)
                    .collect(Collectors.toList());

            Map<String, Object> pageData = new HashMap<>();
            pageData.put("avenues", avenues);
            pageData.put("currentPage", avenuePage.getNumber());
            pageData.put("totalPages", avenuePage.getTotalPages());
            pageData.put("totalElements", avenuePage.getTotalElements());
            pageData.put("size", avenuePage.getSize());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Deleted avenues retrieved successfully");
            response.put("data", pageData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve deleted avenues: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/admin/deleted/simple")
    public ResponseEntity<Map<String, Object>> getAllDeletedAvenuesSimple() {
        try {
            List<Avenue> avenues = avenueService.getAllDeletedAvenuesSimple();

            List<Map<String, Object>> avenuesList = avenues.stream()
                    .map(this::buildDeletedAvenueResponse)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "All deleted avenues retrieved successfully");
            response.put("data", avenuesList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve deleted avenues: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/admin/deleted/{avenueId}")
    public ResponseEntity<Map<String, Object>> getDeletedAvenueById(@PathVariable Integer avenueId) {
        try {
            Map<String, Object> avenueData = avenueService.getDeletedAvenueWithUserDetails(avenueId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Deleted avenue retrieved successfully");
            response.put("data", avenueData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Deleted avenue not found: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/admin/deleted/search")
    public ResponseEntity<Map<String, Object>> searchDeletedAvenues(
            @RequestParam(required = false) String avenueName,
            @RequestParam(required = false) String avenueSpecial,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String area,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Avenue> avenuePage = avenueService.searchDeletedAvenues(avenueName, avenueSpecial, district, state, area, pageable);

            List<Map<String, Object>> avenues = avenuePage.getContent().stream()
                    .map(this::buildDeletedAvenueResponse)
                    .collect(Collectors.toList());

            Map<String, Object> pageData = new HashMap<>();
            pageData.put("avenues", avenues);
            pageData.put("currentPage", avenuePage.getNumber());
            pageData.put("totalPages", avenuePage.getTotalPages());
            pageData.put("totalElements", avenuePage.getTotalElements());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Search in deleted avenues completed successfully");
            response.put("data", pageData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Search in deleted avenues failed: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // =============== RESTORE ENDPOINTS ===============

    @PostMapping("/admin/deleted/{avenueId}/restore")
    public ResponseEntity<Map<String, Object>> restoreAvenue(
            @PathVariable Integer avenueId,
            @RequestParam String restoredBy) {
        try {
            Avenue avenue = avenueService.restoreAvenue(avenueId, restoredBy);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Avenue restored successfully");
            response.put("data", buildAvenueResponse(avenue));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to restore avenue: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // =============== HELPER METHODS FOR DELETED AVENUES ===============

    private Map<String, Object> buildDeletedAvenueResponse(Avenue avenue) {
        Map<String, Object> avenueData = new HashMap<>();
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

        // Include deletion info
        avenueData.put("createdAt", avenue.getCreatedAt());
        avenueData.put("updatedAt", avenue.getUpdatedAt());
        avenueData.put("deletedAt", avenue.getDeletedAt());
        avenueData.put("createdBy", avenue.getCreatedBy());
        avenueData.put("updatedBy", avenue.getUpdatedBy());
        avenueData.put("deletedBy", avenue.getDeletedBy());

        return avenueData;
    }


    // =============== GET ALL AVENUES ENDPOINTS ===============

    @GetMapping("/admin/all")
    public ResponseEntity<Map<String, Object>> getAllAvenuesForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Avenue> avenuePage = avenueService.getAllAvenues(pageable);

            List<Map<String, Object>> avenues = avenuePage.getContent().stream()
                    .map(this::buildDetailedAvenueResponse)
                    .collect(Collectors.toList());

            Map<String, Object> pageData = new HashMap<>();
            pageData.put("avenues", avenues);
            pageData.put("currentPage", avenuePage.getNumber());
            pageData.put("totalPages", avenuePage.getTotalPages());
            pageData.put("totalElements", avenuePage.getTotalElements());
            pageData.put("size", avenuePage.getSize());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Avenues retrieved successfully");
            response.put("data", pageData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve avenues: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/mobile/all")
    public ResponseEntity<Map<String, Object>> getAllAvenuesForMobile(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Avenue> avenuePage = avenueService.getAllAvenues(pageable);

            List<Map<String, Object>> avenues = avenuePage.getContent().stream()
                    .map(this::buildMobileAvenueResponse)
                    .collect(Collectors.toList());

            Map<String, Object> pageData = new HashMap<>();
            pageData.put("avenues", avenues);
            pageData.put("currentPage", avenuePage.getNumber());
            pageData.put("totalPages", avenuePage.getTotalPages());
            pageData.put("totalElements", avenuePage.getTotalElements());
            pageData.put("size", avenuePage.getSize());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Avenues retrieved successfully");
            response.put("data", pageData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve avenues: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/admin/all/simple")
    public ResponseEntity<Map<String, Object>> getAllAvenuesSimple() {
        try {
            List<Avenue> avenues = avenueService.getAllAvenuesSimple();

            List<Map<String, Object>> avenuesList = avenues.stream()
                    .map(this::buildAvenueResponse)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "All avenues retrieved successfully");
            response.put("data", avenuesList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve avenues: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // =============== SEARCH ENDPOINTS ===============

    @GetMapping("/admin/search")
    public ResponseEntity<Map<String, Object>> searchAvenues(
            @RequestParam(required = false) String avenueName,
            @RequestParam(required = false) String avenueSpecial,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String area,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Avenue> avenuePage = avenueService.searchAvenues(avenueName, avenueSpecial, district, state, area, pageable);

            List<Map<String, Object>> avenues = avenuePage.getContent().stream()
                    .map(this::buildDetailedAvenueResponse)
                    .collect(Collectors.toList());

            Map<String, Object> pageData = new HashMap<>();
            pageData.put("avenues", avenues);
            pageData.put("currentPage", avenuePage.getNumber());
            pageData.put("totalPages", avenuePage.getTotalPages());
            pageData.put("totalElements", avenuePage.getTotalElements());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Search completed successfully");
            response.put("data", pageData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Search failed: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/mobile/search")
    public ResponseEntity<Map<String, Object>> searchAvenuesForMobile(
            @RequestParam(required = false) String avenueName,
            @RequestParam(required = false) String avenueSpecial,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String area,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Avenue> avenuePage = avenueService.searchAvenues(avenueName, avenueSpecial, district, state, area, pageable);

            List<Map<String, Object>> avenues = avenuePage.getContent().stream()
                    .map(this::buildMobileAvenueResponse)
                    .collect(Collectors.toList());

            Map<String, Object> pageData = new HashMap<>();
            pageData.put("avenues", avenues);
            pageData.put("currentPage", avenuePage.getNumber());
            pageData.put("totalPages", avenuePage.getTotalPages());
            pageData.put("totalElements", avenuePage.getTotalElements());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Search completed successfully");
            response.put("data", pageData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Search failed: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    // =============== LOCATION-BASED ENDPOINTS ===============

    @GetMapping("/search/location")
    public ResponseEntity<Map<String, Object>> searchByLocation(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String area,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Avenue> avenuePage = avenueService.searchByLocation(state, district, area, pageable);

            List<Map<String, Object>> avenues = avenuePage.getContent().stream()
                    .map(this::buildMobileAvenueResponse)
                    .collect(Collectors.toList());

            Map<String, Object> pageData = new HashMap<>();
            pageData.put("avenues", avenues);
            pageData.put("currentPage", avenuePage.getNumber());
            pageData.put("totalPages", avenuePage.getTotalPages());
            pageData.put("totalElements", avenuePage.getTotalElements());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Location search completed successfully");
            response.put("data", pageData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Location search failed: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search/nearby")
    public ResponseEntity<Map<String, Object>> findNearbyAvenues(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "10.0") Double radiusKm) {
        try {
            List<Avenue> avenues = avenueService.findNearbyAvenues(latitude, longitude, radiusKm);

            List<Map<String, Object>> avenuesList = avenues.stream()
                    .map(this::buildMobileAvenueResponse)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Nearby avenues found successfully");
            response.put("data", avenuesList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Nearby search failed: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    // Replace the existing unified search endpoint in AvenueController.java
    @GetMapping("/admin/unified-search")
    public ResponseEntity<Map<String, Object>> unifiedSearch(
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "all") String searchType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Avenue> avenuePage = avenueService.unifiedSearch(query, searchType, pageable);

            List<Map<String, Object>> avenues = avenuePage.getContent().stream()
                    .map(this::buildDetailedAvenueResponse)
                    .collect(Collectors.toList());

            Map<String, Object> pageData = new HashMap<>();
            pageData.put("avenues", avenues);
            pageData.put("currentPage", avenuePage.getNumber());
            pageData.put("totalPages", avenuePage.getTotalPages());
            pageData.put("totalElements", avenuePage.getTotalElements());
            pageData.put("size", avenuePage.getSize());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Search completed successfully");
            response.put("data", pageData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Search failed: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    // =============== IMAGE MANAGEMENT ENDPOINTS ===============

    @PostMapping("/admin/{avenueId}/images")
    public ResponseEntity<Map<String, Object>> addAvenueImage(
            @PathVariable Integer avenueId,
            @RequestParam String imageUrl,
            @RequestParam(required = false) String caption,
            @RequestParam String createdBy) {
        try {
            AvenueImage image = avenueService.addAvenueImage(avenueId, imageUrl, caption, createdBy);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Image added successfully");
            response.put("data", buildImageResponse(image));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to add image: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{avenueId}/images")
    public ResponseEntity<Map<String, Object>> getAvenueImages(@PathVariable Integer avenueId) {
        try {
            List<AvenueImage> images = avenueService.getAvenueImages(avenueId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Images retrieved successfully");
            response.put("data", buildImageResponses(images));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve images: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/admin/images/{imageId}")
    public ResponseEntity<Map<String, Object>> updateImageCaption(
            @PathVariable Integer imageId,
            @RequestParam String caption,
            @RequestParam String updatedBy) {
        try {
            AvenueImage image = avenueService.updateAvenueImageCaption(imageId, caption, updatedBy);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Image caption updated successfully");
            response.put("data", buildImageResponse(image));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update image caption: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/admin/images/{imageId}")
    public ResponseEntity<Map<String, Object>> deleteAvenueImage(
            @PathVariable Integer imageId,
            @RequestParam String deletedBy) {
        try {
            avenueService.deleteAvenueImage(imageId, deletedBy);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Image deleted successfully");
            response.put("data", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to delete image: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // =============== HELPER METHODS ===============

    private Map<String, Object> buildAvenueResponse(Avenue avenue) {
        Map<String, Object> avenueData = new HashMap<>();
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
        return avenueData;
    }

    private Map<String, Object> buildMobileAvenueResponse(Avenue avenue) {
        Map<String, Object> avenueData = buildAvenueResponse(avenue);
        // Mobile users don't need audit fields
        return avenueData;
    }

    private Map<String, Object> buildDetailedAvenueResponse(Avenue avenue) {
        Map<String, Object> avenueData = buildAvenueResponse(avenue);
        // Add admin-specific fields
        avenueData.put("createdAt", avenue.getCreatedAt());
        avenueData.put("updatedAt", avenue.getUpdatedAt());
        avenueData.put("createdBy", avenue.getCreatedBy());
        avenueData.put("updatedBy", avenue.getUpdatedBy());
        return avenueData;
    }

    private Map<String, Object> buildImageResponse(AvenueImage image) {
        Map<String, Object> imageData = new HashMap<>();
        imageData.put("imageId", image.getImageId());
        imageData.put("avenueId", image.getAvenueId());
        imageData.put("imageUrl", image.getImageUrl());
        imageData.put("caption", image.getCaption());
        imageData.put("createdAt", image.getCreatedAt());
        return imageData;
    }
    // NEW: Image upload endpoints
    @PostMapping("/admin/{avenueId}/upload-image")
    public ResponseEntity<Map<String, Object>> uploadAvenueImage(
            @PathVariable Integer avenueId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String caption,
            @RequestParam String createdBy) {
        try {
            AvenueImage image = avenueService.uploadAndAddImage(avenueId, file, caption, createdBy);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Image uploaded successfully");
            response.put("data", buildImageResponse(image));
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to upload image: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to upload image: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/admin/{avenueId}/upload-multiple-images")
    public ResponseEntity<Map<String, Object>> uploadMultipleAvenueImages(
            @PathVariable Integer avenueId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(required = false) List<String> captions,
            @RequestParam String createdBy) {
        try {
            List<AvenueImage> images = avenueService.uploadAndAddMultipleImages(avenueId, files, captions, createdBy);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Images uploaded successfully");
            response.put("data", buildImageResponses(images));
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to upload images: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to upload images: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    private List<Map<String, Object>> buildImageResponses(List<AvenueImage> images) {
        return images.stream()
                .map(this::buildImageResponse)
                .collect(Collectors.toList());
    }
}
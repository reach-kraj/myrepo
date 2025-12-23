// AvenueRepository.java
package com.pilgrim.repository;

import com.pilgrim.entity.Avenue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvenueRepository extends JpaRepository<Avenue, Integer> {

    Optional<Avenue> findByAvenueIdAndIsActiveTrue(Integer avenueId);
    List<Avenue> findByIsActiveTrueOrderByCreatedAtDesc();
    Page<Avenue> findByIsActiveTrue(Pageable pageable);

    // Search methods
    Page<Avenue> findByAvenueNameContainingIgnoreCaseAndIsActiveTrue(String avenueName, Pageable pageable);
    Page<Avenue> findByAvenueSpecialContainingIgnoreCaseAndIsActiveTrue(String avenueSpecial, Pageable pageable);
    Page<Avenue> findByDistrictContainingIgnoreCaseAndIsActiveTrue(String district, Pageable pageable);
    Page<Avenue> findByStateContainingIgnoreCaseAndIsActiveTrue(String state, Pageable pageable);
    Page<Avenue> findByAreaContainingIgnoreCaseAndIsActiveTrue(String area, Pageable pageable);

    // Location-based search
    @Query("SELECT a FROM Avenue a WHERE a.isActive = true AND " +
            "(:state IS NULL OR LOWER(a.state) LIKE LOWER(CONCAT('%', :state, '%'))) AND " +
            "(:district IS NULL OR LOWER(a.district) LIKE LOWER(CONCAT('%', :district, '%'))) AND " +
            "(:area IS NULL OR LOWER(a.area) LIKE LOWER(CONCAT('%', :area, '%')))")
    Page<Avenue> findByLocationFilters(@Param("state") String state,
                                       @Param("district") String district,
                                       @Param("area") String area,
                                       Pageable pageable);

    // Nearby search using GPS coordinates
    @Query("SELECT a FROM Avenue a WHERE a.isActive = true AND " +
            "a.gpsLatitude IS NOT NULL AND a.gpsLongitude IS NOT NULL AND " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(a.gpsLatitude)) * " +
            "cos(radians(a.gpsLongitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(a.gpsLatitude)))) <= :radiusKm " +
            "ORDER BY (6371 * acos(cos(radians(:latitude)) * cos(radians(a.gpsLatitude)) * " +
            "cos(radians(a.gpsLongitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(a.gpsLatitude))))")
    List<Avenue> findNearbyAvenues(@Param("latitude") Double latitude,
                                   @Param("longitude") Double longitude,
                                   @Param("radiusKm") Double radiusKm);

    // NEW: Multi-field search
    @Query("SELECT a FROM Avenue a WHERE a.isActive = true AND (" +
            "LOWER(a.avenueName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.avenueSpecial) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.district) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.state) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.area) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Avenue> findByMultiFieldSearch(@Param("query") String query, Pageable pageable);

    // NEW: Location-specific search
    @Query("SELECT a FROM Avenue a WHERE a.isActive = true AND (" +
            "LOWER(a.district) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.state) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.area) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Avenue> findByLocationSearch(@Param("query") String query, Pageable pageable);

    // NEW: Name-specific search
    @Query("SELECT a FROM Avenue a WHERE a.isActive = true AND " +
            "LOWER(a.avenueName) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Avenue> findByAvenueNameContainingIgnoreCase(@Param("query") String query, Pageable pageable);

    // NEW: Special-specific search
    @Query("SELECT a FROM Avenue a WHERE a.isActive = true AND " +
            "LOWER(a.avenueSpecial) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Avenue> findByAvenueSpecialContainingIgnoreCase(@Param("query") String query, Pageable pageable);

// NEW: Methods for deleted avenues

    // Find all deleted avenues
    Page<Avenue> findByIsActiveFalseAndDeletedAtIsNotNull(Pageable pageable);
    List<Avenue> findByIsActiveFalseAndDeletedAtIsNotNullOrderByDeletedAtDesc();

    // Find specific deleted avenue
    Optional<Avenue> findByAvenueIdAndIsActiveFalseAndDeletedAtIsNotNull(Integer avenueId);

    // Search within deleted avenues
    Page<Avenue> findByAvenueNameContainingIgnoreCaseAndIsActiveFalseAndDeletedAtIsNotNull(String avenueName, Pageable pageable);
    Page<Avenue> findByAvenueSpecialContainingIgnoreCaseAndIsActiveFalseAndDeletedAtIsNotNull(String avenueSpecial, Pageable pageable);
    Page<Avenue> findByDistrictContainingIgnoreCaseAndIsActiveFalseAndDeletedAtIsNotNull(String district, Pageable pageable);
    Page<Avenue> findByStateContainingIgnoreCaseAndIsActiveFalseAndDeletedAtIsNotNull(String state, Pageable pageable);
    Page<Avenue> findByAreaContainingIgnoreCaseAndIsActiveFalseAndDeletedAtIsNotNull(String area, Pageable pageable);

    // Multi-field search within deleted avenues
    @Query("SELECT a FROM Avenue a WHERE a.isActive = false AND a.deletedAt IS NOT NULL AND (" +
            "LOWER(a.avenueName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.avenueSpecial) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.district) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.state) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.area) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Avenue> findDeletedByMultiFieldSearch(@Param("query") String query, Pageable pageable);

    // Location-based search within deleted avenues
    @Query("SELECT a FROM Avenue a WHERE a.isActive = false AND a.deletedAt IS NOT NULL AND " +
            "(:state IS NULL OR LOWER(a.state) LIKE LOWER(CONCAT('%', :state, '%'))) AND " +
            "(:district IS NULL OR LOWER(a.district) LIKE LOWER(CONCAT('%', :district, '%'))) AND " +
            "(:area IS NULL OR LOWER(a.area) LIKE LOWER(CONCAT('%', :area, '%')))")
    Page<Avenue> findDeletedByLocationFilters(@Param("state") String state,
                                              @Param("district") String district,
                                              @Param("area") String area,
                                              Pageable pageable);

}
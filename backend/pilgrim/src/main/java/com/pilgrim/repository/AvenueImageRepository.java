// AvenueImageRepository.java
package com.pilgrim.repository;

import com.pilgrim.entity.AvenueImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvenueImageRepository extends JpaRepository<AvenueImage, Integer> {

    List<AvenueImage> findByAvenueIdAndIsActiveTrueOrderByCreatedAtAsc(Integer avenueId);
    Optional<AvenueImage> findByImageIdAndIsActiveTrue(Integer imageId);
    void deleteByAvenueIdAndIsActiveTrue(Integer avenueId);
    long countByAvenueIdAndIsActiveTrue(Integer avenueId);
    // NEW: Methods for deleted images

    // Find all images for an avenue (including deleted ones)
    List<AvenueImage> findByAvenueIdOrderByCreatedAtAsc(Integer avenueId);

    // Find only deleted images for an avenue
    List<AvenueImage> findByAvenueIdAndIsActiveFalseAndDeletedAtIsNotNull(Integer avenueId);

    // Find deleted images with ordering
    List<AvenueImage> findByAvenueIdAndIsActiveFalseAndDeletedAtIsNotNullOrderByDeletedAtDesc(Integer avenueId);

    // Count deleted images for an avenue
    int countByAvenueIdAndIsActiveFalseAndDeletedAtIsNotNull(Integer avenueId);

    // Find all deleted images across all avenues
    List<AvenueImage> findByIsActiveFalseAndDeletedAtIsNotNullOrderByDeletedAtDesc();
    Page<AvenueImage> findByIsActiveFalseAndDeletedAtIsNotNull(Pageable pageable);
}

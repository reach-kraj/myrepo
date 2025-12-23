// Avenue.java
package com.pilgrim.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "avenue")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avenue_id")
    private Integer avenueId;

    @Column(name = "avenue_name", nullable = false, length = 191)
    private String avenueName;

    @Column(name = "avenue_special", nullable = false, length = 191)
    private String avenueSpecial;

    @Column(name = "district", nullable = false, length = 191)
    private String district;

    @Column(name = "state", nullable = false, length = 191)
    private String state;

    @Column(name = "area", length = 191)
    private String area;

    @Column(name = "gps_latitude", precision = 10, scale = 8)
    private BigDecimal gpsLatitude;

    @Column(name = "gps_longitude", precision = 11, scale = 8)
    private BigDecimal gpsLongitude;

    @Column(name = "avenue_timings", length = 300)
    private String avenueTimings;

    @Column(name = "avenue_details", length = 300)
    private String avenueDetails;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "deleted_by", length = 50)
    private String deletedBy;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

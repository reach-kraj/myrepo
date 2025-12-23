
// AvenueRequest.java
package com.pilgrim.mapper;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AvenueRequest {

    @NotBlank(message = "Avenue name is required")
    @Size(max = 191, message = "Avenue name must not exceed 191 characters")
    private String avenueName;

    @NotBlank(message = "Avenue special is required")
    @Size(max = 191, message = "Avenue special must not exceed 191 characters")
    private String avenueSpecial;

    @NotBlank(message = "District is required")
    @Size(max = 191, message = "District must not exceed 191 characters")
    private String district;

    @NotBlank(message = "State is required")
    @Size(max = 191, message = "State must not exceed 191 characters")
    private String state;

    @Size(max = 191, message = "Area must not exceed 191 characters")
    private String area;

    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private BigDecimal gpsLatitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private BigDecimal gpsLongitude;

    @Size(max = 300, message = "Avenue timings must not exceed 300 characters")
    private String avenueTimings;

    @Size(max = 300, message = "Avenue details must not exceed 300 characters")
    private String avenueDetails;

    @NotNull(message = "Created by is required")
    private String createdBy;

    // Image URLs from S3
    private List<AvenueImageRequest> images;
}
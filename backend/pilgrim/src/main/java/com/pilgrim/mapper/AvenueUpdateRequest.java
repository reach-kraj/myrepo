// AvenueUpdateRequest.java
package com.pilgrim.mapper;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AvenueUpdateRequest {

    @NotNull(message = "Avenue ID is required")
    private Integer avenueId;

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

    private BigDecimal gpsLatitude;
    private BigDecimal gpsLongitude;

    @Size(max = 300, message = "Avenue timings must not exceed 300 characters")
    private String avenueTimings;

    @Size(max = 300, message = "Avenue details must not exceed 300 characters")
    private String avenueDetails;

    @NotNull(message = "Updated by is required")
    private String updatedBy;

    // Image operations
    private List<AvenueImageRequest> newImages;        // New images to add
    private List<Integer> deleteImageIds;              // Image IDs to delete
    private List<AvenueImageUpdateRequest> updateImages; // Images to update
}

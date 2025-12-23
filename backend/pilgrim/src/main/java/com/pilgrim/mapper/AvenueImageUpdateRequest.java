// AvenueImageUpdateRequest.java
package com.pilgrim.mapper;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class AvenueImageUpdateRequest {

    @NotNull(message = "Image ID is required")
    private Integer imageId;

    @Size(max = 300, message = "Caption must not exceed 300 characters")
    private String caption;
}
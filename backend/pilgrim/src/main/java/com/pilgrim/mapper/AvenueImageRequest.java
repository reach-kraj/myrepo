// AvenueImageRequest.java
package com.pilgrim.mapper;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class AvenueImageRequest {

    @NotBlank(message = "Image URL is required")
    @Size(max = 300, message = "Image URL must not exceed 300 characters")
    private String imageUrl;

    @Size(max = 300, message = "Caption must not exceed 300 characters")
    private String caption;
}
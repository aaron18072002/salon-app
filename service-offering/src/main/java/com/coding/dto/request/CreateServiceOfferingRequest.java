package com.coding.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateServiceOfferingRequest(
        @NotBlank(message = "Service name is required")
        @Size(max = 100, message = "Service name must not exceed 100 characters")
        String name,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        @Digits(integer = 12, fraction = 0, message = "Price must be a whole number with up to 12 integer digits and 0 decimals")
        BigDecimal price,

        @NotNull(message = "Duration in minutes is required")
        @Min(value = 1, message = "Duration must be at least 1 minute")
        Integer durationMinutes,

        @NotNull(message = "Salon ID is required")
        UUID salonId,

        @NotNull(message = "Category ID is required")
        Long categoryId,

        List<@NotBlank(message = "Image URL cannot be blank") String> images

) {};

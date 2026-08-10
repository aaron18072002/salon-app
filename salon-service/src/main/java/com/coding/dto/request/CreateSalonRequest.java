package com.coding.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalTime;
import java.util.List;

public record CreateSalonRequest(

        @NotBlank(message = "Salon name is required")
        @Size(max = 100, message = "Salon name must not exceed 100 characters")
        String name,

        @Size(max = 15, message = "You can upload a maximum of 15 images")
        List<@NotBlank(message = "Image URL cannot be blank") String> images,

        @NotBlank(message = "Address is required")
        @Size(max = 255, message = "Address must not exceed 255 characters")
        String address,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^[0-9]{10}$",
                message = "Phone number must be exactly 10 digits long (e.g., 0123456789). Spaces, dashes, plus signs, and other characters are not allowed."
        )
        String phoneNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        String email,

        @NotBlank(message = "City is required")
        @Size(max = 100, message = "City must not exceed 100 characters")
        String city,

        @NotNull(message = "Owner ID is required")
        @Positive(message = "Owner ID must be a positive number")
        Long ownerId,

        @NotNull(message = "Opening time is required")
        LocalTime openingTime,

        @NotNull(message = "Closing time is required")
        LocalTime closingTime

) {}

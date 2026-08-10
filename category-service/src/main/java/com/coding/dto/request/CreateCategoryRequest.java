package com.coding.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateCategoryRequest(
        @NotBlank(message = "Category name must not be blank")
        String name,

        List<String> images,

        @NotNull(message = "Salon ID must not be null")
        UUID salonId
) { }

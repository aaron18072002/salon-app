package com.coding.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateCategoryRequest(
        @NotBlank(message = "Category name must not be blank")
        String name,

        List<String> images
) {}

package com.coding.dto.response;

import com.coding.model.UserRole;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        String phoneNumber,
        UserRole role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

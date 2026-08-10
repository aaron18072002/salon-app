package com.coding.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CategoryResponse(
        Long id,
        String name,
        List<String> images,
        UUID salonId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

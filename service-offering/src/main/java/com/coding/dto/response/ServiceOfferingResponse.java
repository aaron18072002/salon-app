package com.coding.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ServiceOfferingResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer durationMinutes,
        UUID salonId,
        Long categoryId,
        List<String> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

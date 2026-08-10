package com.coding.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record SalonResponse(
        UUID id,
        String name,
        List<String> images,
        String address,
        String phoneNumber,
        String email,
        String city,
        Long ownerId,
        LocalTime openingTime,
        LocalTime closingTime,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
)
{};

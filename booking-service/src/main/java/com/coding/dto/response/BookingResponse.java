package com.coding.dto.response;

import com.coding.domain.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record BookingResponse(
        UUID id,
        UUID salonId,
        Long customerId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Set<Long> serviceIds,
        BookingStatus status,
        int totalServices,
        BigDecimal totalPrice,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

package com.coding.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record CreateBookingRequest(

        @NotNull(message = "Salon ID is required")
        UUID salonId,

        @NotNull(message = "Customer ID is required")
        @Positive(message = "Customer ID must be a positive number")
        Long customerId,

        @NotNull(message = "Start time is required")
        @FutureOrPresent(message = "Start time must be in the present or future")
        LocalDateTime startTime,

        @NotNull(message = "End time is required")
        @Future(message = "End time must be in the future")
        LocalDateTime endTime,

        @NotEmpty(message = "At least one service must be selected")
        Set<@NotNull(message = "Service ID cannot be null")
            @Positive(message = "Service ID must be a positive number") Long> serviceIds

) {}

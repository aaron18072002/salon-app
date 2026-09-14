package com.coding.dto.request;

import com.coding.domain.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentOrderRequest {

    @NotNull(message = "Booking ID is required")
    private UUID bookingId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Salon ID is required")
    private UUID salonId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Long amount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

}

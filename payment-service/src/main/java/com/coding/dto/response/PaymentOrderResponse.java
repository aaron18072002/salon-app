package com.coding.dto.response;

import com.coding.domain.PaymentMethod;
import com.coding.domain.PaymentOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderResponse {

    private UUID orderId;
    private Long amount;
    private PaymentOrderStatus status;
    private PaymentMethod paymentMethod;
    private String paymentLinkId;
    private String paymentUrl;
    private Long userId;
    private UUID bookingId;
    private UUID salonId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

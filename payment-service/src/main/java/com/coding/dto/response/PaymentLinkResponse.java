package com.coding.dto.response;

public record PaymentLinkResponse(
        String paymentLinkUrl,
        String getPaymentLinkId
) {
}

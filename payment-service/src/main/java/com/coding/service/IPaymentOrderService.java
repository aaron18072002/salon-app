package com.coding.service;

import com.coding.dto.request.CreatePaymentOrderRequest;
import com.coding.dto.response.PaymentOrderResponse;

import com.coding.domain.PaymentOrderStatus;

import java.util.List;
import java.util.UUID;

public interface IPaymentOrderService {

    PaymentOrderResponse createPaymentOrder(CreatePaymentOrderRequest request);

    PaymentOrderResponse getPaymentOrderById(UUID orderId);

    PaymentOrderResponse getPaymentOrderByBookingId(UUID bookingId);

    List<PaymentOrderResponse> getPaymentOrdersByUserId(Long userId);

    PaymentOrderResponse updatePaymentOrderStatus(UUID orderId, PaymentOrderStatus status);

}

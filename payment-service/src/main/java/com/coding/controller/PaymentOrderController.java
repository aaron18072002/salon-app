package com.coding.controller;

import com.coding.domain.PaymentOrderStatus;
import com.coding.dto.request.CreatePaymentOrderRequest;
import com.coding.dto.response.ApiResponse;
import com.coding.dto.response.PaymentOrderResponse;
import com.coding.service.IPaymentOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentOrderController {

    private final IPaymentOrderService paymentOrderService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PaymentOrderResponse>> createPaymentOrder(
            @Valid @RequestBody CreatePaymentOrderRequest request) {
        PaymentOrderResponse response = paymentOrderService.createPaymentOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<PaymentOrderResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Payment order created successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<PaymentOrderResponse>> getPaymentOrderById(
            @PathVariable UUID orderId) {
        PaymentOrderResponse response = paymentOrderService.getPaymentOrderById(orderId);
        return ResponseEntity.ok(
                ApiResponse.<PaymentOrderResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Payment order retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ApiResponse<PaymentOrderResponse>> getPaymentOrderByBookingId(
            @PathVariable UUID bookingId) {
        PaymentOrderResponse response = paymentOrderService.getPaymentOrderByBookingId(bookingId);
        return ResponseEntity.ok(
                ApiResponse.<PaymentOrderResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Payment order retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<PaymentOrderResponse>>> getPaymentOrdersByUserId(
            @PathVariable Long userId) {
        List<PaymentOrderResponse> response = paymentOrderService.getPaymentOrdersByUserId(userId);
        return ResponseEntity.ok(
                ApiResponse.<List<PaymentOrderResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("User payment orders retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<PaymentOrderResponse>> updatePaymentOrderStatus(
            @PathVariable UUID orderId,
            @RequestParam PaymentOrderStatus status) {
        PaymentOrderResponse response = paymentOrderService.updatePaymentOrderStatus(orderId, status);
        return ResponseEntity.ok(
                ApiResponse.<PaymentOrderResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Payment order status updated successfully")
                        .data(response)
                        .build()
        );
    }

}

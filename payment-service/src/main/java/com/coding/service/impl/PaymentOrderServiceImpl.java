package com.coding.service.impl;

import com.coding.dto.request.CreatePaymentOrderRequest;
import com.coding.dto.response.PaymentOrderResponse;
import com.coding.exception.ResourceNotFoundException;
import com.coding.domain.PaymentMethod;
import com.coding.mapper.PaymentOrderMapper;
import com.coding.domain.PaymentOrderStatus;
import com.coding.model.PaymentOrder;
import com.coding.repository.PaymentOrderRepository;
import com.coding.service.IPaymentOrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentOrderServiceImpl implements IPaymentOrderService {

    private final PaymentOrderRepository paymentOrderRepository;
    private final PaymentOrderMapper paymentOrderMapper;

    @Value("${stripe.success-url:http://localhost:3000/payment/success?session_id={CHECKOUT_SESSION_ID}}")
    private String successUrl;

    @Value("${stripe.cancel-url:http://localhost:3000/payment/cancel}")
    private String cancelUrl;

    @Override
    @Transactional
    public PaymentOrderResponse createPaymentOrder(CreatePaymentOrderRequest request) {
        log.info("Initiating payment order for bookingId: {}, userId: {}, amount: {}, method: {}",
                request.getBookingId(), request.getUserId(), request.getAmount(), request.getPaymentMethod());

        // Check if there is already a SUCCESS payment for this booking
        paymentOrderRepository.findByBookingId(request.getBookingId()).ifPresent(existingOrder -> {
            if (existingOrder.getStatus() == PaymentOrderStatus.SUCCESS) {
                throw new IllegalStateException
                        ("Payment has already been completed for booking ID: " + request.getBookingId());
            }
        });

        PaymentOrder paymentOrder = paymentOrderMapper.toEntity(request);
        paymentOrder.setStatus(PaymentOrderStatus.PENDING);

        String checkoutUrl = null;

        if (request.getPaymentMethod() == PaymentMethod.STRIPE) {
            try {
                Session session = createStripeCheckoutSession(request);
                paymentOrder.setPaymentLinkId(session.getId());
                checkoutUrl = session.getUrl();
                log.info("Stripe checkout session created with ID: {}", session.getId());
            } catch (StripeException e) {
                log.error("Failed to create Stripe Checkout Session for booking {}: {}",
                        request.getBookingId(), e.getMessage(), e);
                throw new RuntimeException("Error initializing Stripe payment: " + e.getUserMessage(), e);
            }
        }

        PaymentOrder savedOrder = paymentOrderRepository.save(paymentOrder);
        PaymentOrderResponse response = paymentOrderMapper.toResponse(savedOrder);
        response.setPaymentUrl(checkoutUrl);

        return response;
    }

    private Session createStripeCheckoutSession(CreatePaymentOrderRequest request) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
           .setMode(SessionCreateParams.Mode.PAYMENT)
           .setSuccessUrl(successUrl)
           .setCancelUrl(cancelUrl)
           .addLineItem(
              SessionCreateParams.LineItem.builder()
                 .setQuantity(1L)
                 .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                       .setCurrency("usd")
                       .setUnitAmount(request.getAmount()) // in smallest currency unit (e.g., cents)
                       .setProductData(
                          SessionCreateParams.LineItem.PriceData.ProductData.builder()
                             .setName("Salon Service Booking")
                             .setDescription("Booking Appointment ID: " + request.getBookingId())
                                  .build()).build()).build()
                )
                .putMetadata("bookingId", request.getBookingId().toString())
                .putMetadata("userId", request.getUserId().toString())
                .putMetadata("salonId", request.getSalonId().toString())
                .build();

        return Session.create(params);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentOrderResponse getPaymentOrderById(UUID orderId) {
        PaymentOrder paymentOrder = paymentOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Payment order not found with ID: " + orderId));
        return paymentOrderMapper.toResponse(paymentOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentOrderResponse getPaymentOrderByBookingId(UUID bookingId) {
        PaymentOrder paymentOrder = paymentOrderRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Payment order not found for booking ID: " + bookingId));
        return paymentOrderMapper.toResponse(paymentOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentOrderResponse> getPaymentOrdersByUserId(Long userId) {
        return paymentOrderRepository.findByUserId(userId)
                .stream()
                .map(paymentOrderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PaymentOrderResponse updatePaymentOrderStatus(UUID orderId, PaymentOrderStatus status) {
        log.info("Updating payment order {} status to {}", orderId, status);
        PaymentOrder paymentOrder = paymentOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment order not found with ID: " + orderId));
        paymentOrder.setStatus(status);
        PaymentOrder savedOrder = paymentOrderRepository.save(paymentOrder);
        return paymentOrderMapper.toResponse(savedOrder);
    }
}

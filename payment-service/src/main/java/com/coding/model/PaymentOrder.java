package com.coding.model;

import com.coding.domain.PaymentMethod;
import com.coding.domain.PaymentOrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "PAYMENT_ORDERS")
public class PaymentOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", updatable = false, nullable = false)
    private UUID orderId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    @Builder.Default
    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 30)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_link_id")
    private String paymentLinkId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "booking_id", nullable = false)
    private UUID bookingId;

    @Column(name = "salon_id", nullable = false)
    private UUID salonId;

}

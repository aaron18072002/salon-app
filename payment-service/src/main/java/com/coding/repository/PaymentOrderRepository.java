package com.coding.repository;

import com.coding.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, UUID> {

    Optional<PaymentOrder> findByPaymentLinkId(String paymentLinkId);

    Optional<PaymentOrder> findByBookingId(UUID bookingId);

    List<PaymentOrder> findByUserId(Long userId);

    List<PaymentOrder> findBySalonId(UUID salonId);

}

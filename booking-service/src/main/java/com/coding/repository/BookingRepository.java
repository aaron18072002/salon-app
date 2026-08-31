package com.coding.repository;

import com.coding.domain.BookingStatus;
import com.coding.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findByCustomerId(Long customerId);

    List<Booking> findBySalonId(UUID salonId);

    List<Booking> findBySalonIdAndStatus(UUID salonId, BookingStatus status);

    List<Booking> findByCustomerIdAndStatus(Long customerId, BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.salonId = :salonId " +
           "AND b.status != :cancelledStatus " +
           "AND b.startTime < :endTime AND b.endTime > :startTime")
    List<Booking> findOverlappingBookings(
            @Param("salonId") UUID salonId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("cancelledStatus") BookingStatus cancelledStatus
    );

    @Query("SELECT b FROM Booking b WHERE b.salonId = :salonId " +
           "AND b.startTime >= :startTime AND b.endTime <= :endTime")
    List<Booking> searchBookingsBySalonAndDateRange(
            @Param("salonId") UUID salonId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

}

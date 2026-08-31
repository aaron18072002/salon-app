package com.coding.service;

import com.coding.domain.BookingStatus;
import com.coding.dto.request.CreateBookingRequest;
import com.coding.dto.request.UpdateBookingRequest;
import com.coding.dto.response.BookingResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IBookingService {

    /**
     * Creates a new booking appointment.
     */
    BookingResponse createBooking(CreateBookingRequest request);

    /**
     * Updates an existing booking's time slot, services, or status.
     */
    BookingResponse updateBooking(UUID bookingId, UpdateBookingRequest request);

    /**
     * Updates the status of an existing booking (e.g., CONFIRMED, CANCELLED).
     */
    BookingResponse updateBookingStatus(UUID bookingId, BookingStatus status);

    /**
     * Retrieves a booking by its unique identifier.
     */
    BookingResponse getBookingById(UUID bookingId);

    /**
     * Retrieves all bookings across the platform.
     */
    List<BookingResponse> getAllBookings();

    /**
     * Retrieves all bookings associated with a specific customer.
     */
    List<BookingResponse> getBookingsByCustomerId(Long customerId);

    /**
     * Retrieves all bookings for a specific salon.
     */
    List<BookingResponse> getBookingsBySalonId(UUID salonId);

    /**
     * Retrieves all bookings for a specific salon filtered by status.
     */
    List<BookingResponse> getBookingsBySalonIdAndStatus(UUID salonId, BookingStatus status);

    /**
     * Retrieves all bookings for a specific customer filtered by status.
     */
    List<BookingResponse> getBookingsByCustomerIdAndStatus(Long customerId, BookingStatus status);

    /**
     * Searches bookings within a salon for a given date/time window.
     */
    List<BookingResponse> searchBookingsBySalonAndDateRange(
            UUID salonId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    /**
     * Deletes/cancels a booking by its unique identifier.
     */
    void deleteBookingById(UUID bookingId);

}


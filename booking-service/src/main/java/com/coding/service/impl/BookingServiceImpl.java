package com.coding.service.impl;

import com.coding.client.ServiceOfferingClient;
import com.coding.domain.BookingStatus;
import com.coding.dto.request.CreateBookingRequest;
import com.coding.dto.request.UpdateBookingRequest;
import com.coding.dto.response.ApiResponse;
import com.coding.dto.response.BookingResponse;
import com.coding.dto.response.ServiceOfferingDTO;
import com.coding.exception.DuplicateResourceException;
import com.coding.exception.ResourceNotFoundException;
import com.coding.mapper.BookingMapper;
import com.coding.model.Booking;
import com.coding.repository.BookingRepository;
import com.coding.service.IBookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final ServiceOfferingClient serviceOfferingClient;

    @Override
    public BookingResponse createBooking(CreateBookingRequest request) {
        if (request.startTime().isAfter(request.endTime()) || request.startTime().isEqual(request.endTime())) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }

        // Slot collision check: Ensure no active overlapping booking exists
        List<Booking> overlapping = this.bookingRepository.findOverlappingBookings(
                request.salonId(),
                request.startTime(),
                request.endTime(),
                BookingStatus.CANCELLED
        );
        if (!overlapping.isEmpty()) {
            throw new DuplicateResourceException
                    ("The requested appointment time slot is already booked for this salon.");
        }

        // Fetch services and calculate totalPrice from service-offering microservice
        BigDecimal totalPrice = this.calculateTotalPrice(request.serviceIds());

        Booking booking = this.bookingMapper.toEntity(request);
        booking.setStatus(BookingStatus.PENDING);
        booking.setTotalPrice(totalPrice);
        booking.setTotalServices(request.serviceIds() != null ? request.serviceIds().size() : 0);

        Booking savedBooking = this.bookingRepository.save(booking);
        return this.bookingMapper.toResponse(savedBooking);
    }

    @Override
    public BookingResponse updateBooking(UUID bookingId, UpdateBookingRequest request) {
        Booking booking = this.bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        if (request.startTime().isAfter(request.endTime()) || request.startTime().isEqual(request.endTime())) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }

        // Update targeted booking object from UpdateBookingRequest
        this.bookingMapper.updateBookingFromRequest(request, booking);

        // Recalculate totalPrice and totalServices if serviceIds were updated
        if (request.serviceIds() != null && !request.serviceIds().isEmpty()) {
            BigDecimal totalPrice = this.calculateTotalPrice(request.serviceIds());
            booking.setTotalPrice(totalPrice);
            booking.setTotalServices(request.serviceIds().size());
        }

        Booking updatedBooking = this.bookingRepository.saveAndFlush(booking);

        return this.bookingMapper.toResponse(updatedBooking);
    }

    @Override
    public BookingResponse updateBookingStatus(UUID bookingId, BookingStatus status) {
        Booking booking = this.bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Booking not found with ID: " + bookingId));

        booking.setStatus(status);
        Booking updatedBooking = this.bookingRepository.saveAndFlush(booking);

        return this.bookingMapper.toResponse(updatedBooking);
    }

    @Override
    public BookingResponse getBookingById(UUID bookingId) {
        return this.bookingRepository.findById(bookingId)
                .map(this.bookingMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Booking not found with ID: " + bookingId));
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return this.bookingRepository.findAll()
                .stream()
                .map(this.bookingMapper::toResponse)
                .toList();
    }

    @Override
    public List<BookingResponse> getBookingsByCustomerId(Long customerId) {
        return this.bookingRepository.findByCustomerId(customerId)
                .stream()
                .map(this.bookingMapper::toResponse)
                .toList();
    }

    @Override
    public List<BookingResponse> getBookingsBySalonId(UUID salonId) {
        return this.bookingRepository.findBySalonId(salonId)
                .stream()
                .map(this.bookingMapper::toResponse)
                .toList();
    }

    @Override
    public List<BookingResponse> getBookingsBySalonIdAndStatus(UUID salonId, BookingStatus status) {
        return this.bookingRepository.findBySalonIdAndStatus(salonId, status)
                .stream()
                .map(this.bookingMapper::toResponse)
                .toList();
    }

    @Override
    public List<BookingResponse> getBookingsByCustomerIdAndStatus(Long customerId, BookingStatus status) {
        return this.bookingRepository.findByCustomerIdAndStatus(customerId, status)
                .stream()
                .map(this.bookingMapper::toResponse)
                .toList();
    }

    @Override
    public List<BookingResponse> searchBookingsBySalonAndDateRange(
            UUID salonId,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        return this.bookingRepository.searchBookingsBySalonAndDateRange(salonId, startTime, endTime)
                .stream()
                .map(this.bookingMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteBookingById(UUID bookingId) {
        if (!this.bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException("Cannot delete. Booking not found with ID: " + bookingId);
        }
        this.bookingRepository.deleteById(bookingId);
    }

    private BigDecimal calculateTotalPrice(Set<Long> serviceIds) {
        if (serviceIds == null || serviceIds.isEmpty()) {
            return BigDecimal.ZERO;
        }

        ApiResponse<Set<ServiceOfferingDTO>> response =
                this.serviceOfferingClient.getServiceOfferingsByIds(serviceIds);
        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            throw new ResourceNotFoundException
                    ("No valid services found for the provided IDs: " + serviceIds);
        }

        Set<ServiceOfferingDTO> serviceOfferings = response.getData();
        if (serviceOfferings.size() != serviceIds.size()) {
            throw new ResourceNotFoundException
                    ("One or more selected services could not be found in service offerings.");
        }

        return serviceOfferings.stream()
                .map(ServiceOfferingDTO::price)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}

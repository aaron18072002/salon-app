package com.coding.controller;

import com.coding.domain.BookingStatus;
import com.coding.dto.request.CreateBookingRequest;
import com.coding.dto.request.UpdateBookingRequest;
import com.coding.dto.response.ApiResponse;
import com.coding.dto.response.BookingResponse;
import com.coding.service.IBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final IBookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody CreateBookingRequest request) {
        BookingResponse response = this.bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<BookingResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Booking created successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponse>> updateBooking(
            @PathVariable UUID bookingId,
            @Valid @RequestBody UpdateBookingRequest request) {
        BookingResponse response = this.bookingService.updateBooking(bookingId, request);
        return ResponseEntity.ok(
                ApiResponse.<BookingResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Booking updated successfully")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping("/{bookingId}/status")
    public ResponseEntity<ApiResponse<BookingResponse>> updateBookingStatus(
            @PathVariable UUID bookingId,
            @RequestParam(name = "status") BookingStatus status) {
        BookingResponse response = this.bookingService.updateBookingStatus(bookingId, status);
        return ResponseEntity.ok(
                ApiResponse.<BookingResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Booking status updated successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(
            @PathVariable UUID bookingId) {
        BookingResponse response = this.bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(
                ApiResponse.<BookingResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched booking successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings() {
        List<BookingResponse> responses = this.bookingService.getAllBookings();
        return ResponseEntity.ok(
                ApiResponse.<List<BookingResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched all bookings successfully")
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getBookingsByCustomerId(
            @PathVariable Long customerId,
            @RequestParam(name = "status", required = false) BookingStatus status) {
        List<BookingResponse> responses = (status != null)
                ? this.bookingService.getBookingsByCustomerIdAndStatus(customerId, status)
                : this.bookingService.getBookingsByCustomerId(customerId);

        return ResponseEntity.ok(
                ApiResponse.<List<BookingResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched bookings for customer successfully")
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getBookingsBySalonId(
            @PathVariable UUID salonId,
            @RequestParam(name = "status", required = false) BookingStatus status) {
        List<BookingResponse> responses = (status != null)
                ? this.bookingService.getBookingsBySalonIdAndStatus(salonId, status)
                : this.bookingService.getBookingsBySalonId(salonId);

        return ResponseEntity.ok(
                ApiResponse.<List<BookingResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched bookings for salon successfully")
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/salon/{salonId}/search")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> searchBookingsBySalonAndDateRange(
            @PathVariable UUID salonId,
            @RequestParam(name = "startTime")
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                LocalDateTime startTime,
            @RequestParam(name = "endTime")
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                LocalDateTime endTime) {
        List<BookingResponse> responses = this.bookingService
                .searchBookingsBySalonAndDateRange(salonId, startTime, endTime);
        return ResponseEntity.ok(
                ApiResponse.<List<BookingResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Search bookings completed successfully")
                        .data(responses)
                        .build()
        );
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Void>> deleteBookingById(
            @PathVariable UUID bookingId) {
        this.bookingService.deleteBookingById(bookingId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Booking deleted successfully")
                        .data(null)
                        .build()
        );
    }

}

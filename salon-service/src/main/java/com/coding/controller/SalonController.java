package com.coding.controller;

import com.coding.dto.request.CreateSalonRequest;
import com.coding.dto.request.UpdateSalonRequest;
import com.coding.dto.response.ApiResponse;
import com.coding.dto.response.SalonResponse;
import com.coding.service.ISalonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/salons")
@RequiredArgsConstructor
public class SalonController {

    private final ISalonService salonService;

    @PostMapping
    public ResponseEntity<ApiResponse<SalonResponse>> createSalon
            (@Valid @RequestBody CreateSalonRequest request) {
        SalonResponse response = this.salonService.createSalon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<SalonResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Salon created successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{salonId}")
    public ResponseEntity<ApiResponse<SalonResponse>> updateSalon(
            @PathVariable UUID salonId,
            @Valid @RequestBody UpdateSalonRequest request) {
        // Will change this logic by spring security later
        SalonResponse response = this.salonService.updateSalon(salonId, 1L, request);
        return ResponseEntity.ok(
                ApiResponse.<SalonResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Salon updated successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SalonResponse>>> getAllSalons() {
        List<SalonResponse> responses = this.salonService.getAllSalons();
        return ResponseEntity.ok(
                ApiResponse.<List<SalonResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched all salons")
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<ApiResponse<SalonResponse>> getSalonById
            (@PathVariable UUID salonId) {
        SalonResponse response = this.salonService.getSalonById(salonId);
        return ResponseEntity.ok(
                ApiResponse.<SalonResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched salon successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<SalonResponse>>> getSalonsByOwnerId
            (@PathVariable Long ownerId) {
        List<SalonResponse> responses = this.salonService.getSalonsByOwnerId(ownerId);
        return ResponseEntity.ok(
                ApiResponse.<List<SalonResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched salons for owner")
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SalonResponse>>> searchSalonsByKeyword
            (@RequestParam(name = "keyword") String keyword) {
        List<SalonResponse> responses = this.salonService.searchSalonsByKeyword(keyword);
        return ResponseEntity.ok(
                ApiResponse.<List<SalonResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Search salons completed")
                        .data(responses)
                        .build()
        );
    }

    @DeleteMapping("/{salonId}")
    public ResponseEntity<ApiResponse<Void>> deleteSalonById
            (@PathVariable UUID salonId) {
        this.salonService.deleteSalonById(salonId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Salon deleted successfully")
                        .data(null)
                        .build()
        );
    }

}

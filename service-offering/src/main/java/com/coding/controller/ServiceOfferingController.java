package com.coding.controller;

import com.coding.dto.request.CreateServiceOfferingRequest;
import com.coding.dto.request.UpdateServiceOfferingRequest;
import com.coding.dto.response.ApiResponse;
import com.coding.dto.response.ServiceOfferingResponse;
import com.coding.service.IServiceOffering;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/service-offerings")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final IServiceOffering serviceOfferingService;

    @PostMapping
    public ResponseEntity<ApiResponse<ServiceOfferingResponse>> createServiceOffering(
            @Valid @RequestBody CreateServiceOfferingRequest request) {
        ServiceOfferingResponse response = this.serviceOfferingService.createServiceOffering(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ServiceOfferingResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Service offering created successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceOfferingResponse>> getServiceOfferingById(
            @PathVariable Long id) {
        ServiceOfferingResponse response = this.serviceOfferingService.getServiceOfferingById(id);
        return ResponseEntity.ok(
                ApiResponse.<ServiceOfferingResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched service offering successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceOfferingResponse>> updateServiceOffering(
            @PathVariable Long id,
            @Valid @RequestBody UpdateServiceOfferingRequest request) {
        ServiceOfferingResponse response = this.serviceOfferingService.updateServiceOffering(id, request);
        return ResponseEntity.ok(
                ApiResponse.<ServiceOfferingResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Service offering updated successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<ApiResponse<List<ServiceOfferingResponse>>> getServicesBySalonId(
            @PathVariable UUID salonId) {
        List<ServiceOfferingResponse> responses = this.serviceOfferingService.getAllServicesBySalonId(salonId);
        return ResponseEntity.ok(
                ApiResponse.<List<ServiceOfferingResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched service offerings for salon successfully")
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ServiceOfferingResponse>>> getServicesByCategoryId(
            @PathVariable Long categoryId) {
        List<ServiceOfferingResponse> responses = this.serviceOfferingService.getServicesByCategoryId(categoryId);
        return ResponseEntity.ok(
                ApiResponse.<List<ServiceOfferingResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched service offerings for category successfully")
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/salon/{salonId}/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ServiceOfferingResponse>>> getServicesBySalonIdAndCategoryId(
            @PathVariable UUID salonId,
            @PathVariable Long categoryId) {
        List<ServiceOfferingResponse> responses = this.serviceOfferingService
                .getServicesBySalonIdAndCategoryId(salonId, categoryId);
        return ResponseEntity.ok(
                ApiResponse.<List<ServiceOfferingResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched service offerings by salon and category successfully")
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<ApiResponse<Set<ServiceOfferingResponse>>> getServiceOfferingsByIds(
            @PathVariable Set<Long> ids) {
        Set<ServiceOfferingResponse> responses = this.serviceOfferingService.getServicesByIds(ids);
        return ResponseEntity.ok(
                ApiResponse.<Set<ServiceOfferingResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched service offerings by IDs successfully")
                        .data(responses)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteServiceOffering(
            @PathVariable Long id) {
        this.serviceOfferingService.deleteServiceOffering(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Service offering deleted successfully")
                        .data(null)
                        .build()
        );
    }

}

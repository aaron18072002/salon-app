package com.coding.controller;

import com.coding.dto.request.CreateCategoryRequest;
import com.coding.dto.request.UpdateCategoryRequest;
import com.coding.dto.response.ApiResponse;
import com.coding.dto.response.CategoryResponse;
import com.coding.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory
            (@Valid @RequestBody CreateCategoryRequest request) {
        CategoryResponse categoryResponse = this.categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CategoryResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Category created successfully")
                        .data(categoryResponse)
                        .build()
                );
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById
            (@PathVariable Long categoryId) {
        CategoryResponse categoryResponse = this.categoryService.getCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CategoryResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched category successfully")
                        .data(categoryResponse)
                        .build()
                );
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategoriesBySalonId(
            @PathVariable UUID salonId) {
        List<CategoryResponse> responses = this.categoryService.getAllCategoriesBySalonId(salonId);
        return ResponseEntity.ok(
                ApiResponse.<List<CategoryResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched categories for salon successfully")
                        .data(responses)
                        .build()
        );
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody
            UpdateCategoryRequest request) {
        CategoryResponse response = this.categoryService.updateCategory(categoryId, request);
        return ResponseEntity.ok(
                ApiResponse.<CategoryResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Category updated successfully")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {
        this.categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Category deleted successfully")
                        .data(null)
                        .build()
        );
    }

}

package com.coding.service;

import com.coding.dto.request.CreateCategoryRequest;
import com.coding.dto.request.UpdateCategoryRequest;
import com.coding.dto.response.CategoryResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing categories within a salon.
 */
public interface ICategoryService {

    /**
     * Creates a new category.
     *
     * @param request the request containing category details to be created
     * @return the created category's response data
     */
    CategoryResponse createCategory(CreateCategoryRequest request);

    /**
     * Retrieves all categories associated with a specific salon.
     *
     * @param salonId the unique identifier of the salon
     * @return a list of categories belonging to the specified salon
     */
    List<CategoryResponse> getAllCategoriesBySalonId(UUID salonId);

    /**
     * Retrieves a specific category by its ID.
     *
     * @param categoryId the unique identifier of the category
     * @return the category response data
     */
    CategoryResponse getCategoryById(Long categoryId);

    /**
     * Updates an existing category.
     *
     * @param categoryId the unique identifier of the category to update
     * @param request the request containing the updated category details
     * @return the updated category's response data
     */
    CategoryResponse updateCategory(Long categoryId, UpdateCategoryRequest request);

    /**
     * Deletes a category by its ID.
     *
     * @param categoryId the unique identifier of the category to delete
     */
    void deleteCategory(Long categoryId);

}

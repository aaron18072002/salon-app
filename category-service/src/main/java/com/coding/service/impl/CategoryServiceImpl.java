package com.coding.service.impl;

import com.coding.dto.request.CreateCategoryRequest;
import com.coding.dto.request.UpdateCategoryRequest;
import com.coding.dto.response.CategoryResponse;
import com.coding.exception.ResourceNotFoundException;
import com.coding.mapper.CategoryMapper;
import com.coding.model.Category;
import com.coding.repository.CategoryRepository;
import com.coding.service.ICategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = this.categoryMapper.toEntity(request);
        Category savedCategory = this.categoryRepository.save(category);
        return this.categoryMapper.toResponse(savedCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategoriesBySalonId(UUID salonId) {
        return this.categoryRepository.findBySalonId(salonId)
                .stream()
                .map(this.categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        return this.categoryRepository
                .findById(categoryId)
                .map(this.categoryMapper::toResponse)
                .orElseThrow(() -> new  ResourceNotFoundException("Category not found with ID: " + categoryId));
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, UpdateCategoryRequest request) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

        this.categoryMapper.updateCategoryFromRequest(request, category);
        Category updatedCategory = this.categoryRepository.saveAndFlush(category);

        return this.categoryMapper.toResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if (!this.categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Cannot delete. Category not found with ID: " + categoryId);
        }
        this.categoryRepository.deleteById(categoryId);
    }

}

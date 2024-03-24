package istad.co.samplespringmvc.service;

import istad.co.samplespringmvc.dto.CategoryRequest;
import istad.co.samplespringmvc.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse findCategoryById(int id);
    void deleteCategory(int id);
    CategoryResponse updateCategory(int id, CategoryRequest categoryRequest);
}

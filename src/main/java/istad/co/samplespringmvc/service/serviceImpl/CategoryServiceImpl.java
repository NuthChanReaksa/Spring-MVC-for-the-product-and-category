package istad.co.samplespringmvc.service.serviceImpl;

import istad.co.samplespringmvc.dto.CategoryResponse;
import istad.co.samplespringmvc.dto.CategoryRequest;
import istad.co.samplespringmvc.model.Category;
import istad.co.samplespringmvc.repository.CategoryRepository;
import istad.co.samplespringmvc.repository.ProductRepository;
import istad.co.samplespringmvc.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private Category searchCategoryByID(int id) {
        return categoryRepository.getAllCategories()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Category doesn't exist!!"));
    }

    private CategoryResponse mapCategoryToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }

    private Category mapRequestToCategory(CategoryRequest request) {
        return Category.builder()
                .title(request.title())
                .description(request.description())
                .build();
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.getAllCategories()
                .stream()
                .map(this::mapCategoryToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category newCategory = mapRequestToCategory(request);
        var maxID = categoryRepository.getAllCategories()
                .stream()
                .max(Comparator.comparingInt(Category::getId))
                .map(Category::getId);
        int newID = 1;
        if (maxID.isPresent()) {
            newID = maxID.get() + 1;
        }
        newCategory.setId(newID);
        categoryRepository.addCategory(newCategory);
        return mapCategoryToResponse(newCategory);
    }

    @Override
    public CategoryResponse findCategoryById(int id) {
        return mapCategoryToResponse(searchCategoryByID(id));
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteCategories(id);
    }

    @Override
    public CategoryResponse updateCategory(int id, CategoryRequest categoryRequest) {
        Category result = searchCategoryByID(id);
        result.setTitle(categoryRequest.title());
        result.setDescription(categoryRequest.description());
        categoryRepository.updateCategory(result);
        return mapCategoryToResponse(result);
    }
}

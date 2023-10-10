package com.beneti.productapi.modules.category.services;

import com.beneti.productapi.config.exceptions.ValidationException;
import com.beneti.productapi.config.response.SuccessResponse;
import com.beneti.productapi.modules.category.dtos.CategoryRequest;
import com.beneti.productapi.modules.category.dtos.CategoryResponse;
import com.beneti.productapi.modules.category.models.Category;
import com.beneti.productapi.modules.category.repositories.CategoryRepository;
import com.beneti.productapi.modules.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ProductService productService;

    public Category findById(Integer id) {
        validateId(id);
        return repository.findById(id).orElseThrow(() -> new ValidationException("There is no category for the given ID"));
    }

    public CategoryResponse findByIdResponse(Integer id) {
        return CategoryResponse.of(findById(id));
    }

    public List<CategoryResponse> findAll() {
        return repository
                .findAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public CategoryResponse save(CategoryRequest request) {
        validateCategoryDescription(request);
        var category = repository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    public CategoryResponse update(CategoryRequest request, Integer id) {
        validateCategoryDescription(request);
        validateId(id);
        var category = Category.of(request);
        category.setId(id);
        repository.save(category);
        return CategoryResponse.of(category);
    }

    public SuccessResponse delete(Integer id) {
        validateId(id);
        if(productService.existsByCategoryId(id)) {
            throw new ValidationException("You cannot delete this category, because it's already defined by a product");
        }
        repository.deleteById(id);
        return SuccessResponse.create("The category was deleted!");
    }

    private void validateId(Integer id) {
        if(isEmpty(id)) {
            throw new ValidationException("There is no supplier for the given ID");
        }
    }

    private void validateCategoryDescription(CategoryRequest request) {
        if(isEmpty(request.getDescription())) {
            throw new ValidationException("The category description was not informed!");
        }
    }

}

package com.beneti.productapi.modules.product.services;

import com.beneti.productapi.config.exceptions.ValidationException;
import com.beneti.productapi.config.response.SuccessResponse;
import com.beneti.productapi.modules.category.services.CategoryService;
import com.beneti.productapi.modules.product.dtos.ProductRequest;
import com.beneti.productapi.modules.product.dtos.ProductResponse;
import com.beneti.productapi.modules.product.models.Product;
import com.beneti.productapi.modules.product.repositories.ProductRepository;
import com.beneti.productapi.modules.supplier.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProductService {

    private static final Integer ZERO = 0;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    public Product findById(Integer id) {
        if(isEmpty(id)) {
            throw new ValidationException("There is no product for the given ID");
        }
        return repository.findById(id).orElseThrow(() -> new ValidationException("There is no product for the given ID"));
    }

    public ProductResponse findByIdResponse(Integer id) {
        return ProductResponse.of(findById(id));
    }

    public List<ProductResponse> findByName(String name) {
        if(isEmpty(name)) {
            throw new ValidationException("There is no product for the given name");
        }
        return repository
                .findByNameIgnoreCaseContaining(name)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplierId(Integer supplierId) {
        if(isEmpty(supplierId)) {
            throw new ValidationException("There is no product for the given supplier ID");
        }
        return repository
                .findBySupplierId(supplierId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        if(isEmpty(categoryId)) {
            throw new ValidationException("There is no product for the given category ID");
        }
        return repository
                .findByCategoryId(categoryId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findAll() {
        return repository
                .findAll()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public ProductResponse save(ProductRequest request) {
        validateProductData(request);
        validateCategoryAndSupplierId(request);
        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());
        var product = repository.save(Product.of(request, category, supplier));
        return ProductResponse.of(product);
    }

    public ProductResponse update(ProductRequest request, Integer id) {
        validateProductData(request);
        validateCategoryAndSupplierId(request);
        validateId(id);
        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());
        var product = Product.of(request, category, supplier);
        product.setId(id);
        repository.save(product);
        return ProductResponse.of(product);
    }

    public SuccessResponse delete(Integer id) {
        validateId(id);
        repository.deleteById(id);
        return SuccessResponse.create("The product was deleted!");
    }

    public Boolean existsByCategoryId(Integer categoryId) {
        return repository.existsByCategoryId(categoryId);
    }

    public Boolean existsBySupplierId(Integer supplierId) {
        return repository.existsBySupplierId(supplierId);
    }

    private void validateId(Integer id) {
        if(isEmpty(id)) {
            throw new ValidationException("There is no supplier for the given ID");
        }
    }

    private void validateProductData(ProductRequest request) {
        if(isEmpty(request.getName())) {
            throw new ValidationException("The product name was not informed!");
        }
        if(isEmpty(request.getQuantityAvailable())) {
            throw new ValidationException("The product's quantity was not informed!");
        }
        if(request.getQuantityAvailable() <= ZERO) {
            throw new ValidationException("The product's quantity must be more than 0!");
        }
    }

    private void validateCategoryAndSupplierId(ProductRequest request) {
        if(isEmpty(request.getCategoryId())) {
            throw new ValidationException("The category id was not informed!");
        }

        if(isEmpty(request.getSupplierId())) {
            throw new ValidationException("The supplier id was not informed!");
        }
    }
}

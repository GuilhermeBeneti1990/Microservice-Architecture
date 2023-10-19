package com.beneti.productapi.modules.product.services;

import com.beneti.productapi.config.exceptions.ValidationException;
import com.beneti.productapi.config.response.SuccessResponse;
import com.beneti.productapi.modules.category.services.CategoryService;
import com.beneti.productapi.modules.product.dtos.*;
import com.beneti.productapi.modules.product.models.Product;
import com.beneti.productapi.modules.product.repositories.ProductRepository;
import com.beneti.productapi.modules.sales.client.SalesClient;
import com.beneti.productapi.modules.sales.dtos.SalesConfirmationDTO;
import com.beneti.productapi.modules.sales.enums.SalesStatus;
import com.beneti.productapi.modules.sales.rabbitmq.SalesConfirmationSender;
import com.beneti.productapi.modules.supplier.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    @Autowired
    private SalesConfirmationSender salesConfirmationSender;

    @Autowired
    private SalesClient salesClient;

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

    @Transactional
    public void updateProductStock(ProductStockDTO product) {
        try {
            validateStockUpdateData(product);
            var productsForUpdate = new ArrayList<Product>();
            product.getProducts()
                    .forEach(salesProduct -> {
                        var existingProduct = findById(salesProduct.getProductId());
                        if(salesProduct.getQuantity() > existingProduct.getQuantityAvailable()) {
                            throw new ValidationException(
                                    String.format("The product %s is out of stock", existingProduct.getId())
                            );
                        }
                        existingProduct.updateStock(salesProduct.getQuantity());
                        productsForUpdate.add(existingProduct);
                    });
            if(!isEmpty(productsForUpdate)) {
                repository.saveAll(productsForUpdate);
                var approvedMessage = new SalesConfirmationDTO(product.getSalesId(), SalesStatus.APPROVED);
                salesConfirmationSender.sendSalesConfirmationMessage(approvedMessage);
            }
        } catch (Exception ex) {
            // log.info("Error while trying to update stock for message with error: {}", ex.getMessage(), ex);
            var rejectedMessage = new SalesConfirmationDTO(product.getSalesId(), SalesStatus.REJECTED);
            salesConfirmationSender.sendSalesConfirmationMessage(rejectedMessage);
        }
    }

    public ProductSalesResponse findProductsSales(Integer id) {
        var product = findById(id);
        try {
            var sales = salesClient
                    .findSalesByProductId(product.getId())
                    .orElseThrow(() -> new ValidationException("The sales was not found by this product."));
            return ProductSalesResponse.of(product, sales.getSalesIds());
        } catch (Exception ex){
            throw new ValidationException("There was an error typing to get the product's sales!");
        }
    }

    public SuccessResponse checkProductStock(ProductCheckStockRequest request) {
        if(isEmpty(request) || isEmpty(request.getProducts())) {
            throw new ValidationException("The request data must be informed!");
        }
        request
                .getProducts()
                .forEach(this::validateStock);

        return SuccessResponse.create("The stock is ok!");

    }

    private void validateStock(ProductQuantityDTO productQuantity) {
        if(isEmpty(productQuantity.getProductId()) || isEmpty(productQuantity.getQuantity())) {
            throw new ValidationException("Product ID and quantity must be informed!");
        }
        var product = findById(productQuantity.getProductId());
        if(productQuantity.getQuantity() > product.getQuantityAvailable()) {
            throw new ValidationException(
                    String.format("The product %s is out of stock.")
            );
        }
    }

    private void validateStockUpdateData(ProductStockDTO product) {
        if(isEmpty(product) || isEmpty(product.getSalesId())) {
            throw new ValidationException("The product data and sales ID cannot be null");
        }
        if(isEmpty(product.getProducts())) {
            throw new ValidationException("The sale's products must be informed");
        }
        product
                .getProducts()
                .forEach(salesProduct -> {
                    if(isEmpty(salesProduct.getQuantity()) || isEmpty(salesProduct.getProductId())) {
                        throw new ValidationException("The product ID and the quantity must be informed");
                    }
                });
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

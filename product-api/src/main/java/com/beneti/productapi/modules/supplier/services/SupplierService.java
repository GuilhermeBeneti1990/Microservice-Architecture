package com.beneti.productapi.modules.supplier.services;

import com.beneti.productapi.config.exceptions.ValidationException;
import com.beneti.productapi.config.response.SuccessResponse;
import com.beneti.productapi.modules.product.services.ProductService;
import com.beneti.productapi.modules.supplier.dtos.SupplierRequest;
import com.beneti.productapi.modules.supplier.dtos.SupplierResponse;
import com.beneti.productapi.modules.supplier.models.Supplier;
import com.beneti.productapi.modules.supplier.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository repository;

    @Autowired
    private ProductService productService;

    public Supplier findById(Integer id) {
        validateId(id);
        return repository.findById(id).orElseThrow(() -> new ValidationException("There is no supplier for the given ID"));
    }

    public SupplierResponse findByIdResponse(Integer id) {
        return SupplierResponse.of(findById(id));
    }

    public List<SupplierResponse> findAll() {
        return repository
                .findAll()
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public SupplierResponse save(SupplierRequest request) {
        validateSupplierName(request);
        var supplier = repository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    public SupplierResponse update(SupplierRequest request, Integer id) {
        validateSupplierName(request);
        validateId(id);
        var supplier = Supplier.of(request);
        supplier.setId(id);
        repository.save(supplier);
        return SupplierResponse.of(supplier);
    }

    public SuccessResponse delete(Integer id) {
        validateId(id);
        if(productService.existsBySupplierId(id)) {
            throw new ValidationException("You cannot delete this supplier, because it's already defined by a product");
        }
        repository.deleteById(id);
        return SuccessResponse.create("The supplier was deleted!");
    }

    private void validateId(Integer id) {
        if(isEmpty(id)) {
            throw new ValidationException("There is no supplier for the given ID");
        }
    }

    private void validateSupplierName(SupplierRequest request) {
        if(isEmpty(request.getName())) {
            throw new ValidationException("The supplier name was not informed!");
        }
    }

}

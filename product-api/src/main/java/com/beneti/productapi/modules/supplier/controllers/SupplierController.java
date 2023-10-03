package com.beneti.productapi.modules.supplier.controllers;

import com.beneti.productapi.config.response.SuccessResponse;
import com.beneti.productapi.modules.category.dtos.CategoryRequest;
import com.beneti.productapi.modules.category.dtos.CategoryResponse;
import com.beneti.productapi.modules.supplier.dtos.SupplierRequest;
import com.beneti.productapi.modules.supplier.dtos.SupplierResponse;
import com.beneti.productapi.modules.supplier.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService service;

    @PostMapping
    public SupplierResponse save(@RequestBody SupplierRequest request) {
        return service.save(request);
    }

    @GetMapping
    public List<SupplierResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public SupplierResponse findById(@PathVariable Integer id) {
        return service.findByIdResponse(id);
    }

    @PutMapping("{id}")
    public SupplierResponse update(@RequestBody SupplierRequest request, @PathVariable Integer id) {
        return service.update(request, id);
    }

    @DeleteMapping("{id}")
    public SuccessResponse delete(@PathVariable Integer id) {
        return service.delete(id);
    }

}

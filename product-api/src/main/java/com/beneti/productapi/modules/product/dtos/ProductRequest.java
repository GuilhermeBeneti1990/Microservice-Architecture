package com.beneti.productapi.modules.product.dtos;

import com.beneti.productapi.modules.category.dtos.CategoryResponse;
import com.beneti.productapi.modules.supplier.dtos.SupplierResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductRequest {

    public ProductRequest() {
    }

    public ProductRequest(String name, Integer quantityAvailable, Integer supplierId, Integer categoryId) {
        this.name = name;
        this.quantityAvailable = quantityAvailable;
        this.supplierId = supplierId;
        this.categoryId = categoryId;
    }

    private String name;
    @JsonProperty("quantity_available")
    private Integer quantityAvailable;
    private Integer supplierId;
    private Integer categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}

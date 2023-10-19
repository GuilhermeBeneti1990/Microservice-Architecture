package com.beneti.productapi.modules.product.dtos;

import com.beneti.productapi.modules.category.dtos.CategoryResponse;
import com.beneti.productapi.modules.product.models.Product;
import com.beneti.productapi.modules.supplier.dtos.SupplierResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

public class ProductSalesResponse {

    public ProductSalesResponse() {
    }

    public ProductSalesResponse(Integer productId, String name, Integer quantityAvailable, LocalDateTime createdAt, SupplierResponse supplier, CategoryResponse category, List<String> sales) {
        this.productId = productId;
        this.name = name;
        this.quantityAvailable = quantityAvailable;
        this.createdAt = createdAt;
        this.supplier = supplier;
        this.category = category;
        this.sales = sales;
    }

    private Integer productId;
    private String name;
    @JsonProperty("quantity_available")
    private Integer quantityAvailable;
    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private SupplierResponse supplier;
    private CategoryResponse category;
    private List<String> sales;

    public static ProductSalesResponse of(Product product, List<String> sales) {
        var response = new ProductSalesResponse();
        response.setProductId(product.getId());
        response.setName(product.getName());
        response.setQuantityAvailable(product.getQuantityAvailable());
        response.setCreatedAt(product.getCreatedAt());
        response.setSupplier(SupplierResponse.of(product.getSupplier()));
        response.setCategory(CategoryResponse.of(product.getCategory()));
        response.setSales(sales);
        return response;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public SupplierResponse getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierResponse supplier) {
        this.supplier = supplier;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
    }

    public List<String> getSales() {
        return sales;
    }

    public void setSales(List<String> sales) {
        this.sales = sales;
    }
}

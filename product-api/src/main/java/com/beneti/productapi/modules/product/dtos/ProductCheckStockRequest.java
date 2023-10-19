package com.beneti.productapi.modules.product.dtos;

import java.util.List;

public class ProductCheckStockRequest {

    public ProductCheckStockRequest() {
    }

    public ProductCheckStockRequest(List<ProductQuantityDTO> products) {
        this.products = products;
    }

    private List<ProductQuantityDTO> products;

    public List<ProductQuantityDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductQuantityDTO> products) {
        this.products = products;
    }
}

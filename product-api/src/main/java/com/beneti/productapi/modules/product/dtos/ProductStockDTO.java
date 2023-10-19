package com.beneti.productapi.modules.product.dtos;

import java.util.List;

public class ProductStockDTO {

    public ProductStockDTO() {
    }

    public ProductStockDTO(String salesId, List<ProductQuantityDTO> products) {
        this.salesId = salesId;
        this.products = products;
    }

    private String salesId;
    private List<ProductQuantityDTO> products;

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public List<ProductQuantityDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductQuantityDTO> products) {
        this.products = products;
    }
}

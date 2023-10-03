package com.beneti.productapi.modules.supplier.dtos;

public class SupplierRequest {

    public SupplierRequest() {
    }

    public SupplierRequest(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

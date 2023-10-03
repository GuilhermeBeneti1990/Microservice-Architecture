package com.beneti.productapi.modules.supplier.dtos;

import com.beneti.productapi.modules.supplier.models.Supplier;
import org.springframework.beans.BeanUtils;

public class SupplierResponse {

    public SupplierResponse() {
    }

    public SupplierResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private Integer id;
    private String name;

    public static SupplierResponse of(Supplier supplier) {
        var response = new SupplierResponse();
        BeanUtils.copyProperties(supplier, response);
        return response;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

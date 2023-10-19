package com.beneti.productapi.modules.sales.dtos;

import java.util.List;

public class SalesProductResponse {

    public SalesProductResponse() {
    }

    public SalesProductResponse(List<String> salesIds) {
        this.salesIds = salesIds;
    }

    private List<String> salesIds;

    public List<String> getSalesIds() {
        return salesIds;
    }

    public void setSalesIds(List<String> salesIds) {
        this.salesIds = salesIds;
    }
}

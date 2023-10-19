package com.beneti.productapi.modules.sales.dtos;

import com.beneti.productapi.modules.sales.enums.SalesStatus;

public class SalesConfirmationDTO {

    public SalesConfirmationDTO() {
    }

    public SalesConfirmationDTO(String salesId, SalesStatus status) {
        this.salesId = salesId;
        this.status = status;
    }

    private String salesId;
    private SalesStatus status;

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public SalesStatus getStatus() {
        return status;
    }

    public void setStatus(SalesStatus status) {
        this.status = status;
    }
}

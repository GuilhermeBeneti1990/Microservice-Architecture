package com.beneti.productapi.modules.category.dtos;

public class CategoryRequest {

    public CategoryRequest() {
    }

    public CategoryRequest(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

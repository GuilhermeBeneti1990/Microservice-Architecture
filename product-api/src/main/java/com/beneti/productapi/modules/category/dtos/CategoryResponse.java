package com.beneti.productapi.modules.category.dtos;

import com.beneti.productapi.modules.category.models.Category;
import org.springframework.beans.BeanUtils;

public class CategoryResponse {

    public CategoryResponse() {
    }

    public CategoryResponse(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    private Integer id;
    private String description;

    public static CategoryResponse of(Category category) {
        var response = new CategoryResponse();
        BeanUtils.copyProperties(category, response);
        return response;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

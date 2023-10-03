package com.beneti.productapi.modules.category.models;

import com.beneti.productapi.modules.category.dtos.CategoryRequest;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    public Category() {
    }

    public Category(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(nullable = false)
    private String description;

    public static Category of(CategoryRequest request) {
        var category = new Category();
        BeanUtils.copyProperties(request, category);
        return category;
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

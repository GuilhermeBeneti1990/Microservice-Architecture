package com.beneti.productapi.modules.product.models;

import com.beneti.productapi.modules.category.models.Category;
import com.beneti.productapi.modules.product.dtos.ProductRequest;
import com.beneti.productapi.modules.supplier.models.Supplier;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    public Product() {
    }

    public Product(Integer id, String name, Category category, Supplier supplier, Integer quantityAvailable, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.supplier = supplier;
        this.quantityAvailable = quantityAvailable;
        this.createdAt = createdAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_category", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "fk_supplier", nullable = false)
    private Supplier supplier;

    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public static Product of(ProductRequest request, Category category, Supplier supplier) {
        var product = new Product();
        product.setName(request.getName());
        product.setQuantityAvailable(request.getQuantityAvailable());;
        product.setCategory(category);
        product.setSupplier(supplier);
        return product;
    }

    public void updateStock(Integer quantity) {
        this.quantityAvailable = quantityAvailable - quantity;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
}

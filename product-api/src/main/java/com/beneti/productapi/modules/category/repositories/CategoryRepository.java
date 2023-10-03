package com.beneti.productapi.modules.category.repositories;

import com.beneti.productapi.modules.category.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

package com.beneti.productapi.modules.product.repositories;

import com.beneti.productapi.modules.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameIgnoreCaseContaining(String name);

    List<Product> findByCategoryId(Integer id);

    List<Product> findBySupplierId(Integer id);

    Boolean existsByCategoryId(Integer id);

    Boolean existsBySupplierId(Integer id);

}

package com.beneti.productapi.modules.supplier.repositories;

import com.beneti.productapi.modules.supplier.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}

package com.beneti.productapi.modules.supplier.models;

import com.beneti.productapi.modules.supplier.dtos.SupplierRequest;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Table(name = "suppliers")
public class Supplier {

    public Supplier() {
    }

    public Supplier(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(nullable = false)
    private String name;

    public static Supplier of(SupplierRequest request) {
        var supplier = new Supplier();
        BeanUtils.copyProperties(request, supplier);
        return supplier;
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

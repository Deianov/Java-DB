package com.productshop.model.entity;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(nullable = false, length = 15)
    private String name;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Product> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}

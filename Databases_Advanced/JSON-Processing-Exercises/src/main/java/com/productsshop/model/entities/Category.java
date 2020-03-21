package com.productsshop.model.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    private String name;
    private Set<Product> products;


    @Column(nullable = false, length = 15)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "categories")
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}

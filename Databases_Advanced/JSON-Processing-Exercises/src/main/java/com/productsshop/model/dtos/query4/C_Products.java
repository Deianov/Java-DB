package com.productsshop.model.dtos.query4;

import com.google.gson.annotations.Expose;

import java.util.List;

public class C_Products {

    @Expose
    private Integer count;

    @Expose
    List<D_Product> products;

    public C_Products(Integer count, List<D_Product> products) {
        this.count = count;
        this.products = products;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<D_Product> getProducts() {
        return products;
    }

    public void setProducts(List<D_Product> products) {
        this.products = products;
    }
}
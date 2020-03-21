package com.productsshop.model.dtos.query3;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;


public class CategoryProductsModel{
    @Expose
    private String name;

    @Expose
    private Integer productsCount;

    @Expose
    private double averagePrice;

    @Expose
    private BigDecimal totalRevenue;

    public CategoryProductsModel(String name, Integer productsCount, double averagePrice, BigDecimal totalRevenue) {
        this.name = name;
        this.productsCount = productsCount;
        this.averagePrice = averagePrice;
        this.totalRevenue = totalRevenue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}

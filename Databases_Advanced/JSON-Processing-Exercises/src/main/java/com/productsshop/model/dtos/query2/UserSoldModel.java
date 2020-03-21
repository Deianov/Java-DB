package com.productsshop.model.dtos.query2;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UserSoldModel {
    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private List<ProductBoughtModel> sold;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductBoughtModel> getSold() {
        return sold;
    }

    public void setSold(List<ProductBoughtModel> sold) {
        this.sold = sold;
    }
}

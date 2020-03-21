package com.productsshop.model.dtos.query4;

import com.google.gson.annotations.Expose;


public class BC_User {
    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private Integer age;

    @Expose
    private C_Products soldProducts;

    public BC_User(String firstName, String lastName, Integer age, C_Products soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = soldProducts;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public C_Products getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(C_Products soldProducts) {
        this.soldProducts = soldProducts;
    }
}

package com.productsshop.model.dtos.query4;

import com.google.gson.annotations.Expose;

import java.util.List;

public class BD_User {
    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private Integer age;

    @Expose
    private List<D_Product> sold;

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

    public List<D_Product> getSold() {
        return sold;
    }

    public void setSold(List<D_Product> sold) {
        this.sold = sold;
    }
}

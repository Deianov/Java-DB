package com.productsshop.model.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CategoryDto implements Serializable {

    @Expose
    private String name;

    public CategoryDto() { }

    @NotNull(message = "Category name cannot be null!")
    @Length(min = 3, max = 15, message = "Wrong category name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

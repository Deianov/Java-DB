package com.productsshop.model.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public class ProductDto implements Serializable {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    public ProductDto() {}


    @NotNull(message = "Product name cannot be null!")
    @Length(min = 3, message = "Wrong product name.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Product price cannot be null!")
    @DecimalMin(value = "0", message = "Product price cannot be negative!")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

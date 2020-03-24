package com.productshop.model.dto.view.query4;


import com.productshop.model.dto.view.query2.ProductNamePriceBuyerDto;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductsAndCountDto {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlAttribute
    private Integer age;

    @XmlElement(name = "sold-products")
    private ProductNamePriceRootDto soldProducts;

    public UserSoldProductsAndCountDto() { }

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

    public ProductNamePriceRootDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductNamePriceRootDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}

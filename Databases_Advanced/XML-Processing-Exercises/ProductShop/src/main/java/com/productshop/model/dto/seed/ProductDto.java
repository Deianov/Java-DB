package com.productshop.model.dto.seed;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductDto implements Serializable {

    @XmlElement
    @NotNull(message = "Product name cannot be null!")
    @Length(min = 3, message = "Wrong product name.")
    private String name;

    @XmlElement
    @NotNull(message = "Product price cannot be null!")
    @DecimalMin(value = "0", message = "Product price cannot be negative!")
    private BigDecimal price;

    public ProductDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

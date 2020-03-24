package com.productshop.model.dto.seed;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;


@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryDto{

    @XmlElement
    @NotNull(message = "Category name cannot be null!")
    @Length(min = 3, max = 15, message = "Wrong category name")
    private String name;

    public CategoryDto() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

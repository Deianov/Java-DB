package com.productshop.model.dto.view.query3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryByProductsCountRootDto {

    @XmlElement(name = "category")
    private List<CategoryByProductsCountDto> categories;

    public CategoryByProductsCountRootDto() { }
    public CategoryByProductsCountRootDto(List<CategoryByProductsCountDto> categories) {
        this.categories = categories;
    }

    public List<CategoryByProductsCountDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryByProductsCountDto> categories) {
        this.categories = categories;
    }
}

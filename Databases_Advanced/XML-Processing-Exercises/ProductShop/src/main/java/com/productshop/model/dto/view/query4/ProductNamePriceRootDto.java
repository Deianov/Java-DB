package com.productshop.model.dto.view.query4;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductNamePriceRootDto {

    @XmlAttribute
    private int count;

    @XmlElement(name = "product")
    private List<ProductNamePriceDto> soldProducts;

    public ProductNamePriceRootDto() { }
    public ProductNamePriceRootDto(int count, List<ProductNamePriceDto> soldProducts) {
        this.count = count;
        this.soldProducts = soldProducts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProductNamePriceDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductNamePriceDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}

package com.productshop.model.dto.view.query1;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductNamePriceSellerRootDto implements Serializable {

    @XmlElement(name = "product")
    private List<ProductNamePriceSellerDto> products;

    public ProductNamePriceSellerRootDto() { }
    public ProductNamePriceSellerRootDto(List<ProductNamePriceSellerDto> products) {
        this.products = products;
    }

    public List<ProductNamePriceSellerDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductNamePriceSellerDto> products) {
        this.products = products;
    }
}

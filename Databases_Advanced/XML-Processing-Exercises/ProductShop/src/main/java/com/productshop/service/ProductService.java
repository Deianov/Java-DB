package com.productshop.service;

import com.productshop.model.dto.seed.ProductDto;
import com.productshop.model.dto.view.query1.ProductNamePriceSellerDto;

import java.util.List;


public interface ProductService {
    void seedAll(List<ProductDto> dtos);

    List<ProductNamePriceSellerDto> query1ProductsInRange();
}

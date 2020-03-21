package com.productsshop.service;

import com.productsshop.model.dtos.ProductDto;
import com.productsshop.model.dtos.query1.ProductInRangePriceDto;

import java.util.List;

public interface ProductService {
    void seedAll(ProductDto[] productDtos);
    List<ProductInRangePriceDto> findAllByPriceBetween500And1000();
}

package com.productshop.service;

import com.productshop.model.dto.seed.CategoryDto;
import com.productshop.model.dto.view.query3.CategoryByProductsCountDto;
import com.productshop.model.entity.Category;

import java.util.List;


public interface CategoryService {
    void seedAll(List<CategoryDto> dtos);
    Category getRandom();

    List<CategoryByProductsCountDto> query3CategoriesByProductsCount();

}

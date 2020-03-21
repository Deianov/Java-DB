package com.productsshop.service;

import com.productsshop.model.dtos.CategoryDto;
import com.productsshop.model.dtos.query3.CategoryProductsModel;
import com.productsshop.model.entities.Category;

import java.util.List;

public interface CategoryService {
    void seedAll(CategoryDto[] categoryDtos);

   Category getRandom();
   List<CategoryProductsModel> findAllCategoriesByCountAndPrices();
}

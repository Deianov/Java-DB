package com.productsshop.service;

import com.productsshop.model.dtos.CategoryDto;
import com.productsshop.model.dtos.query3.CategoryProductsModel;
import com.productsshop.model.entities.Category;
import com.productsshop.repository.CategoryRepository;
import com.productsshop.util.RandomUtil;
import com.productsshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final RandomUtil randomUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository, ModelMapper mapper, ValidationUtil validationUtil, RandomUtil randomUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.randomUtil = randomUtil;
    }


    @Override
    public void seedAll(CategoryDto[] categoryDtos) {

        if (this.repository.count() > 0){
            return;
        }

        for (CategoryDto dto : categoryDtos) {

            if(repository.existsByName(dto.getName())){
                continue;
            }

            if(validationUtil.isValid(dto)){
                Category category = mapper.map(dto, Category.class);
                repository.saveAndFlush(category);

            }else {
                validationUtil.printViolations(dto);
            }
        }
    }

    @Override
    public Category getRandom() {
        Long randomId = randomUtil.randomId(this.repository.count()).orElse(null);
        return randomId == null ? null : this.repository.findById(randomId).orElse(null);
    }

    @Override
    public List<CategoryProductsModel> findAllCategoriesByCountAndPrices() {

        return repository.findAllCategoriesByCountAndPrices();
    }
}

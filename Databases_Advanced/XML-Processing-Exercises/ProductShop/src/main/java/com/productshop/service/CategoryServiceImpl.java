package com.productshop.service;

import com.productshop.model.dto.seed.CategoryDto;
import com.productshop.model.dto.view.query3.CategoryByProductsCountDto;
import com.productshop.model.entity.Category;
import com.productshop.repository.CategoryRepository;
import com.productshop.util.RandomUtil;
import com.productshop.util.ValidationUtil;
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
    public void seedAll(List<CategoryDto> dtos) {
        for (CategoryDto dto : dtos) {

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
        long randomId = randomUtil.randomId(this.repository.count());
        return this.repository.findById(randomId).orElse(null);
    }

    @Override
    public List<CategoryByProductsCountDto> query3CategoriesByProductsCount() {
        return repository.findAllCategoriesByCountAndPrices();
    }

}

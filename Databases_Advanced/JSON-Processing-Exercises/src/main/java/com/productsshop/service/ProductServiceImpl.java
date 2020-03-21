package com.productsshop.service;

import com.productsshop.model.dtos.ProductDto;
import com.productsshop.model.dtos.query1.ProductInRangePriceDto;
import com.productsshop.model.entities.Category;
import com.productsshop.model.entities.Product;
import com.productsshop.model.entities.User;
import com.productsshop.repository.ProductRepository;
import com.productsshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, UserService userService, CategoryService categoryService, ModelMapper mapper, ValidationUtil validationUtil) {
        this.repository = repository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public void seedAll(ProductDto[] productDtos) {

        if (this.repository.count() > 0){
            return;
        }

        for (ProductDto dto : productDtos) {

               if(validationUtil.isValid(dto)){
                Product product = mapper.map(dto, Product.class);

                User seller = userService.getRandom();
                User buyer = userService.getRandom();

                product.setSeller(seller);

                if (buyer != null && buyer.getId() % 2 != 0){
                    product.setBuyer(buyer);
                }

                Category category = categoryService.getRandom();

                if(category != null){
                    Set<Category> categories = new HashSet<>();
                    categories.add(category);
                    product.setCategories(categories);
                }
                repository.saveAndFlush(product);

            }else {
                validationUtil.printViolations(dto);
            }
        }

    }

    @Override
    public List<ProductInRangePriceDto> findAllByPriceBetween500And1000() {

        List<Product> products = repository.findAllByPriceBetweenAndBuyerIsNull(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

        List<ProductInRangePriceDto> dtos = new ArrayList<>();

        for (Product product : products) {

            ProductInRangePriceDto dto = mapper.map(product, ProductInRangePriceDto.class);

            dto.setSeller(
                    String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName())
            );

            dtos.add(dto);
        }

        return dtos;
    }
}

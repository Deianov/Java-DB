package com.productshop.service;

import com.productshop.model.dto.seed.ProductDto;
import com.productshop.model.dto.view.query1.ProductNamePriceSellerDto;
import com.productshop.model.entity.Category;
import com.productshop.model.entity.Product;
import com.productshop.model.entity.User;
import com.productshop.repository.ProductRepository;
import com.productshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.productshop.constant.GlobalConstants.*;

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
    public void seedAll(List<ProductDto> dtos) {

        for (ProductDto dto : dtos) {

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
    public List<ProductNamePriceSellerDto> query1ProductsInRange() {

        List<ProductNamePriceSellerDto> list =
                repository
                        .findAllByPriceBetweenAndBuyerIsNull(
                                BigDecimal.valueOf(QUERY1_PRODUCT_RANGE_FROM),
                                BigDecimal.valueOf(QUERY1_PRODUCT_RANGE_TO)
                        )
                .stream()
                .map(obj -> {
                    ProductNamePriceSellerDto dto = mapper.map(obj, ProductNamePriceSellerDto.class);
                    dto.setSellerFullName(String.format("%s %s",
                            obj.getSeller().getFirstName(),
                            obj.getSeller().getLastName())
                    );
                    return dto;
                })
                .collect(Collectors.toList());

        return list;
    }
}

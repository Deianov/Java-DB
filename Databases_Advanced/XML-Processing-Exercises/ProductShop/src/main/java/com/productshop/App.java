package com.productshop;

import com.productshop.model.dto.seed.*;
import com.productshop.model.dto.view.query1.ProductNamePriceSellerRootDto;
import com.productshop.model.dto.view.query2.UserSoldProductsRootDto;
import com.productshop.model.dto.view.query3.CategoryByProductsCountRootDto;
import com.productshop.model.dto.view.query4.UserSoldProductsAndCountDto;
import com.productshop.model.dto.view.query4.UserSoldProductsAndCountRootDto;
import com.productshop.service.CategoryService;
import com.productshop.service.ProductService;
import com.productshop.service.UserService;
import com.productshop.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

import static com.productshop.constant.GlobalConstants.*;


@Component
public class App implements CommandLineRunner {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;
    private final XmlParser xmlParser;

    @Autowired
    public App(CategoryService categoryService, ProductService productService, UserService userService, XmlParser xmlParser) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void run(String... args){

        System.out.println("Start Product Shop application.");
        try {
            this.seedDatabaseFromXmlFiles();

            this.exQuery1();
            this.exQuery2();
            this.exQuery3();
            this.exQuery4();

        }catch (IOException | JAXBException e){
            e.printStackTrace();
        }
    }

    private void exQuery1() throws IOException, JAXBException {

        ProductNamePriceSellerRootDto dto =
                new ProductNamePriceSellerRootDto(productService.query1ProductsInRange());

        xmlParser.marshalToFile(dto, FILE_QUERY1);
        System.out.println(String.format("query: %s", FILE_QUERY1));
    }

    private void exQuery2() throws IOException, JAXBException {

        UserSoldProductsRootDto dto =
                new UserSoldProductsRootDto(userService.query2SuccessfullySoldProducts());

        xmlParser.marshalToFile(dto, FILE_QUERY2);
        System.out.println(String.format("query: %s", FILE_QUERY2));
    }

    private void exQuery3() throws IOException, JAXBException {

        CategoryByProductsCountRootDto dto =
                new CategoryByProductsCountRootDto(categoryService.query3CategoriesByProductsCount());

        xmlParser.marshalToFile(dto, FILE_QUERY3);
        System.out.println(String.format("query: %s", FILE_QUERY3));
    }

    private void exQuery4() throws IOException, JAXBException {
        List<UserSoldProductsAndCountDto> dtos =
                userService.query4UsersAndProducts();

        UserSoldProductsAndCountRootDto dto =
                new UserSoldProductsAndCountRootDto(dtos.size(), dtos);

        xmlParser.marshalToFile(dto, FILE_QUERY4);
        System.out.println(String.format("query: %s", FILE_QUERY4));
    }

    private void seedDatabaseFromXmlFiles() throws IOException, JAXBException {

        if(userService.isFoundUsers()){
            System.out.println("Found database.");
            return;
        }
        System.out.println("Seed database from xml files...");

        UserRootDto rootUser = xmlParser
                .unmarshalFromFile(FILE_PATH_USERS, UserRootDto.class);
        userService.seedAll(rootUser.getUsers());
        userService.setUsersFiends();

        CategoryRootDto rootCategory = xmlParser
                .unmarshalFromFile(FILE_PATH_CATEGORIES, CategoryRootDto.class);
        categoryService.seedAll(rootCategory.getCategories());

        ProductRootDto rootProduct = xmlParser
                .unmarshalFromFile(FILE_PATH_PRODUCTS, ProductRootDto.class);
        productService.seedAll(rootProduct.getProducts());

        System.out.println("Seed Done.");
    }
}

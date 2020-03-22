package com.productsshop;

import com.google.gson.Gson;
import com.productsshop.model.dtos.*;
import com.productsshop.model.dtos.query1.ProductInRangePriceDto;
import com.productsshop.model.dtos.query2.UserSoldModel;
import com.productsshop.model.dtos.query3.CategoryProductsModel;
import com.productsshop.model.dtos.query4.A_Users;
import com.productsshop.service.CategoryService;
import com.productsshop.service.ProductService;
import com.productsshop.service.UserService;
import com.productsshop.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

import static com.productsshop.constant.GlobalConstants.*;


@Component
public class App implements CommandLineRunner {

    private final Gson gson;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;
    private final FileUtil fileUtil;

    @Autowired
    public App(Gson gson, CategoryService categoryService, ProductService productService, UserService userService, FileUtil fileUtil) {
        this.gson = gson;
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
        this.fileUtil = fileUtil;
    }


    @Override
    public void run(String... args) throws Exception {

        System.out.println("Start Product Shop application.");
        try {
            this.seedDatabaseFromJsonFiles();
            System.out.println("Seed Done.");

        }catch (Exception e){
            e.printStackTrace();
        }

        this.exQuery1();
        this.exQuery2();
        this.exQuery3();
        this.exQuery4();
    }

    private void exQuery1() throws IOException {

        List<ProductInRangePriceDto> listDto = productService.findAllByPriceBetween500And1000();
        String json = gson.toJson(listDto);
        fileUtil.write(json, FILE_QUERY1);
    }

    private void exQuery2() throws IOException {

        List<UserSoldModel> list =  userService.findAllUsersWithSoldProducts();
        String json = gson.toJson(list);
        fileUtil.write(json, FILE_QUERY2);
    }

    private void exQuery3() throws IOException {

        List<CategoryProductsModel> list =
                categoryService.findAllCategoriesByCountAndPrices();
        String json = gson.toJson(list);
        fileUtil.write(json, FILE_QUERY3);
    }

    private void exQuery4() throws IOException {

        A_Users a_users = userService.findAllUsersWithSoldProductsAndCount();
        String json = gson.toJson(a_users);
        fileUtil.write(json, FILE_QUERY4);
    }

    private void seedDatabaseFromJsonFiles() throws IOException {

        userService.seedAll(gson
                .fromJson(fileUtil.read(FILE_PATH_USERS), UserDto[].class));

        categoryService.seedAll(gson
                .fromJson(fileUtil.read(FILE_PATH_CATEGORIES), CategoryDto[].class));

        productService.seedAll(gson
                .fromJson(
                        fileUtil.read(FILE_PATH_PRODUCTS), ProductDto[].class));

        userService.setUsersFiends();
    }
}

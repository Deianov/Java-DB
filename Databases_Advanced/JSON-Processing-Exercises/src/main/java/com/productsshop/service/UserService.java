package com.productsshop.service;

import com.productsshop.model.dtos.UserDto;
import com.productsshop.model.dtos.query2.UserSoldModel;
import com.productsshop.model.dtos.query4.A_Users;
import com.productsshop.model.entities.User;

import java.util.List;

public interface UserService {
    void seedAll(UserDto[] userDtos);
    User getRandom();
    void setUsersFiends();

    List<UserSoldModel> findAllUsersWithSoldProducts();

    A_Users findAllUsersWithSoldProductsAndCount();
}

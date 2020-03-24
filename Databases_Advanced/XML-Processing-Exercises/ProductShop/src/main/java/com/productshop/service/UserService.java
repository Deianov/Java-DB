package com.productshop.service;

import com.productshop.model.dto.seed.UserDto;
import com.productshop.model.dto.view.query2.UserSoldProductsDto;
import com.productshop.model.dto.view.query4.UserSoldProductsAndCountDto;
import com.productshop.model.entity.User;

import java.util.List;


public interface UserService {
    boolean isFoundUsers();
    void seedAll(List<UserDto> dtos);
    void setUsersFiends();
    User getRandom();

    List<UserSoldProductsDto> query2SuccessfullySoldProducts();
    List<UserSoldProductsAndCountDto> query4UsersAndProducts();
}

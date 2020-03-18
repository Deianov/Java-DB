package com.softuni.springautomappingdemo.services;

import com.softuni.springautomappingdemo.domain.dtos.UserLoggedDto;
import com.softuni.springautomappingdemo.domain.dtos.UserOwnedDto;
import com.softuni.springautomappingdemo.domain.dtos.UserRegisterDto;
import com.softuni.springautomappingdemo.domain.entities.Game;


public interface UserService {
    String registerUser(UserRegisterDto userRegisterDto);
    UserLoggedDto loginUser(String email, String password);

    String addItemToCart(UserLoggedDto userLoggedDto, Game game);
    String removeItemFromCart(UserLoggedDto userLoggedDto, Game game);
    String buyItemFromCart(UserLoggedDto userLoggedDto);

    UserOwnedDto getOwnedGames(UserLoggedDto userLoggedDto);
}

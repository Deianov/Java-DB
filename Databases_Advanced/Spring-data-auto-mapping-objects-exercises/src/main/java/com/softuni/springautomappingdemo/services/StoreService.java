package com.softuni.springautomappingdemo.services;

import com.softuni.springautomappingdemo.domain.dtos.GameTitleDto;
import com.softuni.springautomappingdemo.domain.dtos.UserLoggedDto;

public interface StoreService {
    boolean isLoggedUser();
    boolean isLoggedAdmin();
    String logout();

    String addItem(GameTitleDto gameTitleDto);
    String removeItem(GameTitleDto gameTitleDto);
    String buyItems();

    String allGames();
    String detailGame(String title);
    String ownedGames();

    // userService
    UserLoggedDto loginUser(String email, String password);
}

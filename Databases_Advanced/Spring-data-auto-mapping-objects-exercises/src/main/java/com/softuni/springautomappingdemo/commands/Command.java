package com.softuni.springautomappingdemo.commands;

import com.softuni.springautomappingdemo.services.GameService;
import com.softuni.springautomappingdemo.services.StoreService;
import com.softuni.springautomappingdemo.services.UserService;

public abstract class Command implements Executable{

    protected final UserService userService;
    protected final GameService gameService;
    protected final StoreService storeService;

    public Command(UserService userService, GameService gameService, StoreService storeService) {
        this.userService = userService;
        this.gameService = gameService;
        this.storeService = storeService;
    }
}


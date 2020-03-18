package com.softuni.springautomappingdemo.commands.store;

import com.softuni.springautomappingdemo.commands.Command;
import com.softuni.springautomappingdemo.services.GameService;
import com.softuni.springautomappingdemo.services.StoreService;
import com.softuni.springautomappingdemo.services.UserService;

public class Logout extends Command {

    public Logout(UserService userService, GameService gameService, StoreService storeService) {
        super(userService, gameService, storeService);
    }

    @Override
    public String execute(String... args) {
        return storeService.logout();
    }
}

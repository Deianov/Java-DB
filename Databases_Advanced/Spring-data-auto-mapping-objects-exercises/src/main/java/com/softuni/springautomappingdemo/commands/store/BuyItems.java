package com.softuni.springautomappingdemo.commands.store;

import com.softuni.springautomappingdemo.commands.Command;
import com.softuni.springautomappingdemo.services.GameService;
import com.softuni.springautomappingdemo.services.StoreService;
import com.softuni.springautomappingdemo.services.UserService;

public class BuyItems extends Command {

    public BuyItems(UserService userService, GameService gameService, StoreService storeService) {
        super(userService, gameService, storeService);
    }

    @Override
    public String execute(String... args) {
        return storeService.buyItems();
    }
}

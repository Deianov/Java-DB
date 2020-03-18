package com.softuni.springautomappingdemo.commands.store;

import com.softuni.springautomappingdemo.commands.Command;
import com.softuni.springautomappingdemo.services.GameService;
import com.softuni.springautomappingdemo.services.StoreService;
import com.softuni.springautomappingdemo.services.UserService;

import static com.softuni.springautomappingdemo.constants.Messages.NOT_FOUND_GAMES;
import static com.softuni.springautomappingdemo.constants.Messages.USER_REQUIRED;

public class OwnedGames extends Command {

    public OwnedGames(UserService userService, GameService gameService, StoreService storeService) {
        super(userService, gameService, storeService);
    }

    @Override
    public String execute(String... args) {

        if (!storeService.isLoggedUser()){
            return USER_REQUIRED;
        }

        if(gameService.getAllGames() == null){
            return NOT_FOUND_GAMES;
        }

        return storeService.ownedGames();
    }
}

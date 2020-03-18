package com.softuni.springautomappingdemo.commands.game;

import com.softuni.springautomappingdemo.commands.Command;
import com.softuni.springautomappingdemo.services.GameService;
import com.softuni.springautomappingdemo.services.StoreService;
import com.softuni.springautomappingdemo.services.UserService;

import static com.softuni.springautomappingdemo.constants.Messages.*;

public class DeleteGame extends Command {
    private final static int INPUT_SIZE = 2;
    private final static int INDEX_ID = 1;

    public DeleteGame(UserService userService, GameService gameService, StoreService storeService) {
        super(userService, gameService, storeService);
    }


    @Override
    public String execute(String... args) {

        if (args == null || args.length < INPUT_SIZE){
            return BAD_INPUT;
        }

        if (!storeService.isLoggedAdmin()){
            return ADMIN_REQUIRED;
        }

        long id;
        try {
            id = Long.parseLong(args[INDEX_ID]);

        } catch (NumberFormatException e){
            return BAD_INPUT;
        }

        return gameService.deleteGameById(id);
    }
}

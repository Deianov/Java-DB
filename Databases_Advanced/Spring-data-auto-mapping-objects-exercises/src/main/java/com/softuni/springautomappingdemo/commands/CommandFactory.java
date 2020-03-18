package com.softuni.springautomappingdemo.commands;

import com.softuni.springautomappingdemo.commands.game.AddGame;
import com.softuni.springautomappingdemo.commands.game.DeleteGame;
import com.softuni.springautomappingdemo.commands.game.EditGame;
import com.softuni.springautomappingdemo.commands.store.*;
import com.softuni.springautomappingdemo.commands.user.*;
import com.softuni.springautomappingdemo.services.GameService;
import com.softuni.springautomappingdemo.services.StoreService;
import com.softuni.springautomappingdemo.services.UserService;
import org.springframework.stereotype.Component;

import static com.softuni.springautomappingdemo.constants.CommandConstants.*;

@Component
public class CommandFactory {

    private final UserService userService;
    private final GameService gameService;
    private final StoreService storeService;

    public CommandFactory(UserService userService, GameService gameService, StoreService storeService) {
        this.userService = userService;
        this.gameService = gameService;
        this.storeService = storeService;
    }

    public Executable createInstance(String commandName){
        switch (commandName){
            case COMMAND_REGISTER:      return new Register(userService, gameService, storeService);
            case COMMAND_LOGIN:         return new Login(userService, gameService, storeService);
            case COMMAND_LOGOUT:        return new Logout(userService, gameService, storeService);
            case COMMAND_ADD_GAME:      return new AddGame(userService, gameService, storeService);
            case COMMAND_EDIT_GAME:     return new EditGame(userService, gameService, storeService);
            case COMMAND_DELETE_GAME:   return new DeleteGame(userService, gameService, storeService);
            case COMMAND_ADD_ITEM:      return new AddItem(userService, gameService, storeService);
            case COMMAND_REMOVE_ITEM:   return new RemoveItem(userService, gameService, storeService);
            case COMMAND_BUY_ITEM:      return new BuyItems(userService, gameService, storeService);
            case COMMAND_ALL_GAMES:     return new AllGames(userService, gameService, storeService);
            case COMMAND_DETAIL_GAME:   return new DetailGame(userService, gameService, storeService);
            case COMMAND_OWNED_GAMES:   return new OwnedGames(userService, gameService, storeService);
        }
        return null;
    }
}

package com.softuni.springautomappingdemo.commands.user;

import com.softuni.springautomappingdemo.commands.Command;
import com.softuni.springautomappingdemo.constants.Messages;
import com.softuni.springautomappingdemo.domain.dtos.UserLoggedDto;
import com.softuni.springautomappingdemo.services.GameService;
import com.softuni.springautomappingdemo.services.StoreService;
import com.softuni.springautomappingdemo.services.UserService;

import static com.softuni.springautomappingdemo.constants.Messages.BAD_INPUT;
import static com.softuni.springautomappingdemo.constants.Messages.USER_LOGGED;

public class Login extends Command {
    private final static int INPUT_SIZE = 3;
    private final static int INDEX_EMAIL = 1;
    private final static int INDEX_PASSWORD = 2;

    public Login(UserService userService, GameService gameService, StoreService storeService) {
        super(userService, gameService, storeService);
    }

    @Override
    public String execute(String... args) {

        if (args == null || args.length < INPUT_SIZE){
            return BAD_INPUT;
        }

        String email = args[INDEX_EMAIL];
        String password = args[INDEX_PASSWORD];

        UserLoggedDto user = storeService.loginUser(email, password);

        if (user == null){
            return Messages.USER_LOGGING_ERROR;
        }

        return String.format(USER_LOGGED, user.getFullName());
    }
}

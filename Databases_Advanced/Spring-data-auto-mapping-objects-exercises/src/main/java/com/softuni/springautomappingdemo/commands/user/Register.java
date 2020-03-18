package com.softuni.springautomappingdemo.commands.user;

import com.softuni.springautomappingdemo.commands.Command;
import com.softuni.springautomappingdemo.domain.dtos.UserRegisterDto;
import com.softuni.springautomappingdemo.services.GameService;
import com.softuni.springautomappingdemo.services.StoreService;
import com.softuni.springautomappingdemo.services.UserService;


import static com.softuni.springautomappingdemo.constants.Messages.*;

public class Register extends Command {
    private final static int INPUT_SIZE = 5;
    private final static int INDEX_EMAIL = 1;
    private final static int INDEX_PASSWORD = 2;
    private final static int INDEX_PASSWORD_REPEAT = 3;
    private final static int INDEX_FULL_NAME = 4;


    public Register(UserService userService, GameService gameService, StoreService storeService) {
        super(userService, gameService, storeService);
    }

    @Override
    public String execute(String... args) {

        if (args == null || args.length < INPUT_SIZE){
            return BAD_INPUT;
        }

        String email = args[INDEX_EMAIL];
        String password = args[INDEX_PASSWORD];
        String passwordRepeat = args[INDEX_PASSWORD_REPEAT];
        String fullName = args[INDEX_FULL_NAME];

        if (!password.equals(passwordRepeat)){
            return PASSWORD_DONT_MATCH;
        }

        UserRegisterDto userRegisterDto =
                new UserRegisterDto(email, password, fullName);

        return userService.registerUser(userRegisterDto);
    }
}

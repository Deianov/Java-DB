package com.softuni.springautomappingdemo.commands.game;

import com.softuni.springautomappingdemo.commands.Command;
import com.softuni.springautomappingdemo.domain.dtos.GameDto;
import com.softuni.springautomappingdemo.domain.validators.CustomValidator;
import com.softuni.springautomappingdemo.services.GameService;
import com.softuni.springautomappingdemo.services.StoreService;
import com.softuni.springautomappingdemo.services.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.softuni.springautomappingdemo.constants.CommandConstants.*;
import static com.softuni.springautomappingdemo.constants.Messages.*;
import static com.softuni.springautomappingdemo.constants.ValidationConstants.*;

public class AddGame extends Command {
    private final static int INPUT_SIZE = 8;
    private final static int INDEX_TITLE = 1;
    private final static int INDEX_PRICE = 2;
    private final static int INDEX_SIZE = 3;
    private final static int INDEX_TRAILER = 4;
    private final static int INDEX_IMAGE = 5;
    private final static int INDEX_DESCRIPTION = 6;
    private final static int INDEX_RELEASE_DATE = 7;

    public AddGame(UserService userService, GameService gameService, StoreService storeService) {
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

        // exists
        if(gameService.existsGameByTitleLike(args[INDEX_TITLE])){
            return GAME_EXIST;
        }

        // create
        GameDto gameDto = new GameDto();
        gameDto.setTitle(args[INDEX_TITLE]);
        gameDto.setPrice(new BigDecimal(Double.parseDouble(args[INDEX_PRICE])));
        gameDto.setSize(Double.parseDouble(args[INDEX_SIZE]));
        gameDto.setTrailer(args[INDEX_TRAILER]);
        gameDto.setImage(args[INDEX_IMAGE]);
        gameDto.setDescription(args[INDEX_DESCRIPTION]);

        LocalDate releaseDate = CustomValidator
                .parseLocalDate(args[INDEX_RELEASE_DATE], DATE_FORMAT, GAME_DATE_REGEX);

        if (releaseDate == null){
            return GAME_DATE_MESSAGE;
        }
        gameDto.setReleaseDate(releaseDate);

        // validate
        String validationResult = gameService.validate(gameDto);
        if (validationResult != null){
            return validationResult;
        }

        return gameService.addGame(gameDto);
    }
}

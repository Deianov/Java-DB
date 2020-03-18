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
import static com.softuni.springautomappingdemo.constants.CommandConstants.INPUT_SEPARATOR_PARAMS;
import static com.softuni.springautomappingdemo.constants.Messages.*;
import static com.softuni.springautomappingdemo.constants.ValidationConstants.*;

public class EditGame extends Command {
    private final static int INPUT_SIZE = 3;
    private final static int INDEX_ID = 1;
    private final static int START_INDEX_VALUES = 2;

    public EditGame(UserService userService, GameService gameService, StoreService storeService) {
        super(userService, gameService, storeService);
    }

    @Override
    public String execute(String... args) {

        if (args == null || args.length < INPUT_SIZE){
            return BAD_INPUT;
        }

        long id = Long.parseLong(args[INDEX_ID]);

        GameDto gameDto = gameService.findGameById(id);

        if(gameDto == null){
            return GAME_NOT_FOUND;
        }

        for (int i = START_INDEX_VALUES; i < args.length ; i++) {

            String[] data = args[i].split(INPUT_SEPARATOR_PARAMS);

            if(data.length != 2){
                return String.format(BAD_INPUT_LINE, args[i]);
            }

            if(!updateField(gameDto, data[0], data[1])){
                return String.format(BAD_INPUT_LINE, args[i]);
            }
        }

        // validate
        String validationResult = gameService.validate(gameDto);
        if (validationResult != null){
            return validationResult;
        }

        return gameService.EditGame(id, gameDto);
    }

    private boolean updateField(GameDto game, String name, String value){

        try {
            switch (name.toLowerCase()){
                case "title":
                    game.setTitle(value);
                    break;
                case "price":
                    game.setPrice(BigDecimal.valueOf(Double.parseDouble(value)));
                    break;
                case "size":
                    game.setSize(Double.parseDouble(value));
                    break;
                case "trailer":
                    game.setTrailer(value);
                    break;
                case "thubnailurl":
                    game.setImage(value);
                    break;
                case "description":
                    game.setDescription(value);
                    break;
                case "releasedate":
                    LocalDate date = CustomValidator
                            .parseLocalDate(value, DATE_FORMAT, GAME_DATE_REGEX);
                    if(date == null){
                        return false;
                    }
                    game.setReleaseDate(date);
                    break;
            }
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}

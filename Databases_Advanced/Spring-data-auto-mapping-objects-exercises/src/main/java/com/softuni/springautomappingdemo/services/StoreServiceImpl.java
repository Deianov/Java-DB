package com.softuni.springautomappingdemo.services;

import com.softuni.springautomappingdemo.domain.dtos.GameDetailViewDto;
import com.softuni.springautomappingdemo.domain.dtos.GameTitleDto;
import com.softuni.springautomappingdemo.domain.dtos.UserLoggedDto;
import com.softuni.springautomappingdemo.domain.dtos.UserOwnedDto;
import com.softuni.springautomappingdemo.domain.entities.Game;
import com.softuni.springautomappingdemo.domain.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.stream.Collectors;

import static com.softuni.springautomappingdemo.constants.Messages.*;

@Service
public class StoreServiceImpl implements StoreService {

    private final UserService userService;
    private final GameService gameService;

    private UserLoggedDto loggedUser = null;

    @Autowired
    public StoreServiceImpl(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public boolean isLoggedUser() {

        return this.getLoggedUser() != null;
    }

    @Override
    public boolean isLoggedAdmin() {
        UserLoggedDto user = this.getLoggedUser();

        return user != null && user.getRole().equals(Role.ADMIN);
    }

    private UserLoggedDto getLoggedUser(){

        if (loggedUser == null){
            return null;
        }

        UserLoggedDto user = userService.loginUser(loggedUser.getEmail(), loggedUser.getPassword());
        loggedUser = user;

        return user;
    }

    @Override
    public UserLoggedDto loginUser(String email, String password) {

        UserLoggedDto user = userService.loginUser(email, password);
        if(user != null){
            this.loggedUser = user;
        }
        return user;
    }

    @Override
    public String logout() {
        if (loggedUser == null){
            return USER_LOGOUT_ERROR;

        }else {
            String result = String
                    .format(USER_LOGOUT, loggedUser.getFullName());
            loggedUser = null;
            return result;
        }
    }

    @Override
    public String addItem(GameTitleDto gameTitleDto) {
        if(!isLoggedUser()){
            return USER_REQUIRED;
        }
        Game game = gameService.findGameByTitle(gameTitleDto.getTitle());
        return
                userService.addItemToCart(this.getLoggedUser(), game);
    }

    @Override
    public String removeItem(GameTitleDto gameTitleDto) {
        if(!isLoggedUser()){
            return USER_REQUIRED;
        }
        Game game = gameService.findGameByTitle(gameTitleDto.getTitle());

        return userService.removeItemFromCart(this.getLoggedUser(), game);
    }

    @Override
    public String buyItems() {
        if(!isLoggedUser()){
            return USER_REQUIRED;
        }
        return userService.buyItemFromCart(this.getLoggedUser());
    }

    @Override
    public String allGames() {
        return
        gameService
                .getAllGames()
                .stream()
                .map(g -> String.format("%s %s",
                        g.getTitle(), g.getPrice()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String detailGame(String title) {

        GameDetailViewDto gameDetailViewDto = gameService
                .getDetailGameByTitle(title);

        if (gameDetailViewDto != null){
            return String.format(
                    "Title: %s%nPrice: %s%nDescription: %s%nRelease date: %s%n",
                    gameDetailViewDto.getTitle(),
                    gameDetailViewDto.getPrice(),
                    gameDetailViewDto.getDescription(),
                    gameDetailViewDto.getReleaseDate()
                    );
        }
        return GAME_NOT_FOUND;
    }

    @Override
    public String ownedGames() {
        if(!isLoggedUser()){
            return USER_REQUIRED;
        }

        UserOwnedDto ownedDto = userService.getOwnedGames(this.getLoggedUser());

        if (ownedDto == null){
            return NOT_FOUND_OWNED_GAMES;
        }
        return String.join(System.lineSeparator(), ownedDto.getGames());
    }
}

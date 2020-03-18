package com.softuni.springautomappingdemo.services;

import com.softuni.springautomappingdemo.domain.dtos.GameBaseViewDto;
import com.softuni.springautomappingdemo.domain.dtos.GameDetailViewDto;
import com.softuni.springautomappingdemo.domain.dtos.GameDto;
import com.softuni.springautomappingdemo.domain.entities.Game;

import java.util.Set;

public interface GameService {
    boolean existsGameByTitleLike(String title);
    boolean existsGameById(Long id);

    String deleteGameById(long id);
    String validate(GameDto gameDto);

    String addGame(GameDto gameDto);
    String EditGame(long id, GameDto gameDto);

    GameDto findGameById(long id);
    Game findGameByTitle(String title);

    Set<GameBaseViewDto> getAllGames();
    GameDetailViewDto getDetailGameByTitle(String title);
}

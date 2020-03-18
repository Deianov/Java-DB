package com.softuni.springautomappingdemo.services;

import com.softuni.springautomappingdemo.domain.dtos.GameBaseViewDto;
import com.softuni.springautomappingdemo.domain.dtos.GameDetailViewDto;
import com.softuni.springautomappingdemo.domain.dtos.GameDto;
import com.softuni.springautomappingdemo.domain.entities.Game;
import com.softuni.springautomappingdemo.repositories.GameRepository;
import com.softuni.springautomappingdemo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

import static com.softuni.springautomappingdemo.constants.Messages.*;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean existsGameByTitleLike(String title) {
        return gameRepository.existsGameByTitleLike(title);
    }

    @Override
    public boolean existsGameById(Long id) {
        return gameRepository.existsGameById(id);
    }

    @Override
    public String addGame(GameDto gameDto) {

        Game game = modelMapper.map(gameDto, Game.class);
        gameRepository.save(game);

        return String.format(GAME_ADDED, game.getTitle());
    }

    @Override
    public String EditGame(long id, GameDto gameDto) {

        // get entity
        Game game = gameRepository.findGameById(id).orElse(null);

        if(game == null){
            return GAME_NOT_FOUND;
        }

        // update entity
        game.setTitle(gameDto.getTitle());
        game.setPrice(gameDto.getPrice());
        game.setSize(gameDto.getSize());
        game.setTrailer(gameDto.getTrailer());
        game.setImage(gameDto.getImage());
        game.setDescription(gameDto.getDescription());
        game.setReleaseDate(gameDto.getReleaseDate());

        // save to database
        gameRepository.save(game);

        return String.format(GAME_EDITED, gameDto.getTitle());
    }

    @Override
    public GameDto findGameById(long id) {

        Game game = gameRepository.findGameById(id).orElse(null);

        if(game != null){
            return  modelMapper.map(game, GameDto.class);
        }
        return null;
    }

    @Override
    public Game findGameByTitle(String title) {
        return
                gameRepository.findGameByTitleLike(title).orElse(null);
    }

    @Override
    public Set<GameBaseViewDto> getAllGames() {
        return gameRepository
                .findAll()
                .stream()
                .map(game -> modelMapper.map(game, GameBaseViewDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public GameDetailViewDto getDetailGameByTitle(String title) {

        Game game = gameRepository.findGameByTitleLike(title).orElse(null);

        if(game != null){
            return modelMapper.map(game, GameDetailViewDto.class);
        }
        return null;
    }

    @Override
    public String deleteGameById(long id) {

        Game game = gameRepository.findGameById(id).orElse(null);

        if (game != null){
            game.getUsers().forEach(user -> user.getGames().remove(game));
            game.getUserCarts().forEach(user -> user.getShoppingCart().remove(game));

            String result = String.format(GAME_DELETED, game.getTitle());
            gameRepository.delete(game);
            return result;
        }
        return GAME_NOT_FOUND;
    }

    @Override
    public String validate(GameDto gameDto) {
        if(!validationUtil.isValid(gameDto)){
            return validationUtil
                    .getViolations(gameDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(System.lineSeparator()));
        }
        return null;
    }
}

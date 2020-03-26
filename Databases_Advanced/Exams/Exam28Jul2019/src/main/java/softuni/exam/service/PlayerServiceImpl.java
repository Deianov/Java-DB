package softuni.exam.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.PlayerSeedDto;
import softuni.exam.domain.entity.Picture;
import softuni.exam.domain.entity.Player;
import softuni.exam.domain.entity.Team;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.JsonParser;
import softuni.exam.util.ValidationUtil;


import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static softuni.exam.constant.GlobalConstants.*;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PictureService pictureService;
    private final TeamService teamService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final JsonParser jsonParser;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PictureService pictureService, TeamService teamService, ModelMapper modelMapper, ValidationUtil validationUtil, JsonParser jsonParser) {
        this.playerRepository = playerRepository;
        this.pictureService = pictureService;
        this.teamService = teamService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.jsonParser = jsonParser;
    }

    @Override
    public String importPlayers() throws IOException {
        StringBuilder result  = new StringBuilder();

        PlayerSeedDto[] dtos = jsonParser
                .objectFromFile(FILE_PATH_PLAYERS, PlayerSeedDto[].class);

        if(dtos == null){
            return NOT_FOUND;
        }

        for (PlayerSeedDto dto : dtos) {

            if(validationUtil.isValid(dto)){

                if(playerRepository
                        .findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName())
                        .isEmpty())
                {
                    Player player = modelMapper.map(dto, Player.class);

                    Picture picture = pictureService
                            .getPictureByUrl(player.getPicture().getUrl());

                    Team team = teamService.getTeamByName(player.getTeam().getName());

                    if (validationUtil.isValid(player) && picture != null && team != null){

                        player.setPicture(picture);
                        player.setTeam(team);
                        playerRepository.saveAndFlush(player);

                        result.append(String.format(IMPORTED_PLAYER,
                                player.getFirstName(),
                                player.getLastName())
                        );

                    } else {
                        result.append(INVALID_PLAYER);
                    }

                } else {
                    result.append(EXISTS);
                }

            } else {
                result.append(INVALID_PLAYER);
            }
            result.append(System.lineSeparator());
        }

       return result.toString();
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return FileUtil.read(FILE_PATH_PLAYERS);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder result = new StringBuilder();

        List<Player> players = playerRepository
                .findAllBySalaryGreaterThanOrderBySalaryDesc(
                        BigDecimal.valueOf(EXPORT_PLAYERS_WITH_SALARY_BIGGER_THAN));

        if(players == null || players.isEmpty()){
            return NOT_FOUND;
        }

        for (Player player : players) {
            result.append(String.format(PLAYER_BY_SALARY_FORMAT,
                    player.getFirstName(),
                    player.getLastName(),
                    player.getNumber(),
                    player.getSalary().toString(),
                    player.getTeam().getName()))
                    .append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder result = new StringBuilder();

        Team team = teamService.getTeamByName(EXPORT_PLAYERS_FROM_TEAM);

        if(team == null){
            return NOT_FOUND;
        }

        result
                .append(String.format("Team: %s", team.getName()))
                .append(System.lineSeparator());

        playerRepository.findAllByTeamName(team.getName())
                .forEach(player -> result
                        .append(String.format(PLAYER_BY_NUMBER_FORMAT,
                                player.getFirstName(),
                                player.getLastName(),
                                player.getPosition().toString(),
                                player.getNumber()))
                        .append(System.lineSeparator())
                );
        return result.toString();
    }
}

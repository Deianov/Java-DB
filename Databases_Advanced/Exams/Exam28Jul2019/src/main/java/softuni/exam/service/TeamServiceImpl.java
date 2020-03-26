package softuni.exam.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.TeamSeedRootDto;
import softuni.exam.domain.entity.Picture;
import softuni.exam.domain.entity.Team;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;


import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import static softuni.exam.constant.GlobalConstants.*;


@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final PictureService pictureService;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, PictureService pictureService) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.pictureService = pictureService;
    }

    @Override
    public String importTeams() throws JAXBException, IOException {
        StringBuilder result = new StringBuilder();

        TeamSeedRootDto rootDto = xmlParser
                .unmarshalFromFile(FILE_PATH_TEAMS, TeamSeedRootDto.class);

        rootDto.getTeams().forEach(dto -> {

            if(validationUtil.isValid(dto)){

                if(getTeamByName(dto.getName()) == null){

                    Team team = modelMapper.map(dto, Team.class);
                    Picture picture = pictureService.getPictureByUrl(team.getPicture().getUrl());

                    if(picture != null){
                        team.setPicture(picture);
                        teamRepository.saveAndFlush(team);
                        result.append(String.format(IMPORTED_TEAM, team.getName()));

                    } else {
                        result.append(INVALID_TEAM);
                    }

                } else {
                    result.append(EXISTS);
                }
            } else {
                result.append(INVALID_TEAM);
            }
            result.append(System.lineSeparator());
        });
       return result.toString();
    }

    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return FileUtil.read(FILE_PATH_TEAMS);
    }

    @Override
    public Team getTeamByName(String name) {
        return teamRepository.findByName(name).orElse(null);
    }
}

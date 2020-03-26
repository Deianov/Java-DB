package softuni.exam.service;

import softuni.exam.domain.entity.Team;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface TeamService {

    String importTeams() throws JAXBException, IOException;

    boolean areImported();

    String readTeamsXmlFile() throws IOException;

    Team getTeamByName(String name);
}

package hiberspring.service;

import hiberspring.domain.entities.Town;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public interface TownService {
    Optional<Town> getByName(String name);

    Boolean townsAreImported();

    String readTownsJsonFile() throws IOException;

    String importTowns(String townsFileContent) throws IOException;
}

package hiberspring.service;

import hiberspring.constant.Constants;
import hiberspring.domain.dtos.TownSeedDto;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.JsonParser;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static hiberspring.constant.Constants.*;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final JsonParser jsonParser;

    @Autowired
    public TownServiceImpl(TownRepository repository, ModelMapper mapper, ValidationUtil validationUtil, JsonParser jsonParser) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.jsonParser = jsonParser;
    }


    @Override
    public Optional<Town> getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Boolean townsAreImported() {
        return repository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return FileUtil.read(FILE_TOWNS);
    }

    @Override
    public String importTowns(String townsFileContent) throws IOException {
        StringBuilder result = new StringBuilder();

        TownSeedDto[] dtos = jsonParser
                .objectFromFile(FILE_TOWNS, TownSeedDto[].class);

        if(dtos == null || dtos.length == 0){
            result.append(NOT_FOUND);
        }

        for (TownSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)){

                if(getByName(dto.getName()).isEmpty()){

                    Town town = mapper.map(dto, Town.class);
                    repository.saveAndFlush(town);

                    result.append(String.format(SUCCESSFUL_IMPORT_MESSAGE,
                            "Town",
                            town.getName())
                    );

                } else {
                    result.append(EXISTS);
                }
            }else {
                result.append(INCORRECT_DATA_MESSAGE);
            }

            result.append(System.lineSeparator());
        }
        return result.toString();
    }
}

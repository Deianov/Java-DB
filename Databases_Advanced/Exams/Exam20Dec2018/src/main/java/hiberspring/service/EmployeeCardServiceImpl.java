package hiberspring.service;

import hiberspring.constant.GlobalConstants;
import hiberspring.domain.dtos.EmployeeCardSeedDto;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.JsonParser;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import static hiberspring.constant.GlobalConstants.*;
import static hiberspring.constant.GlobalConstants.INCORRECT_DATA_MESSAGE;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {

    private final EmployeeCardRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final JsonParser jsonParser;

    @Autowired
    public EmployeeCardServiceImpl(EmployeeCardRepository repository, ModelMapper mapper, ValidationUtil validationUtil, JsonParser jsonParser) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.jsonParser = jsonParser;
    }


    @Override
    public Optional<EmployeeCard> getByNumber(String number) {
        return repository.findByNumber(number);
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return repository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return FileUtil.read(GlobalConstants.FILE_EMPLOYEES_CARDS);
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();

        EmployeeCardSeedDto[] dtos = jsonParser
                .objectFromFile(FILE_EMPLOYEES_CARDS, EmployeeCardSeedDto[].class);

        if(dtos == null || dtos.length == 0){
            result.append(NOT_FOUND);
        }

        for (EmployeeCardSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)){

                if(getByNumber(dto.getNumber()).isEmpty()){

                    EmployeeCard card = mapper.map(dto, EmployeeCard.class);
                    repository.saveAndFlush(card);

                    result.append(String.format(SUCCESSFUL_IMPORT_MESSAGE,
                            "Employee Card",
                            card.getNumber())
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

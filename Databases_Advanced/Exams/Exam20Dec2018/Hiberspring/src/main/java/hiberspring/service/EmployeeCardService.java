package hiberspring.service;

import hiberspring.domain.entities.EmployeeCard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public interface EmployeeCardService {
    Optional<EmployeeCard> getByNumber(String number);

    Boolean employeeCardsAreImported();

    String readEmployeeCardsJsonFile() throws IOException;

    String importEmployeeCards(String employeeCardsFileContent) throws FileNotFoundException;
}

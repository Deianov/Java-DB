package alararestaurant.service;

import alararestaurant.constant.Constants;
import alararestaurant.domain.dtos.EmployeeSeedDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.JsonParser;
import alararestaurant.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final JsonParser jsonParser;
    private final FileUtil fileUtil;
    private final PositionRepository positionRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository, ModelMapper mapper, ValidationUtil validationUtil, JsonParser jsonParser, FileUtil fileUtil, PositionRepository positionRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.jsonParser = jsonParser;
        this.fileUtil = fileUtil;
        this.positionRepository = positionRepository;
    }


    @Override
    public Boolean employeesAreImported() {
        return this.repository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() {
        return fileUtil.readFile(Constants.EMPLOYEES_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder result = new StringBuilder();

        EmployeeSeedDto[] dtos = jsonParser
                .objectFromFile(Constants.EMPLOYEES_FILE_PATH, EmployeeSeedDto[].class);

        if(dtos == null || dtos.length == 0){
            return Constants.NOT_FOUND;
        }

        for (EmployeeSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)){

                if(repository.findByName(dto.getName())
                        .isEmpty())
                {
                    Employee employee = mapper.map(dto, Employee.class);

                    Position position = positionRepository.findByName(dto.getPosition())
                            .orElse(new Position(dto.getPosition()));
                    employee.setPosition(position);

                    repository.saveAndFlush(employee);

                    result.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                            employee.getName())
                    );

                } else {
                    result.append(Constants.EXISTS);
                }

            }else {
                result.append(Constants.INCORRECT_DATA_MESSAGE);
            }

            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public Optional<Employee> getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Employee> getEmployeesByPositionOrderByNameAsc(Position position) {
        return repository.findEmployeesByPositionOrderByNameAsc(position);
    }
}

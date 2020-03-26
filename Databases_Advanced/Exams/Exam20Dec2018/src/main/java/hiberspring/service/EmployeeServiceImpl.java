package hiberspring.service;

import hiberspring.constant.Constants;
import hiberspring.domain.dtos.EmployeeSeedDto;
import hiberspring.domain.dtos.EmployeeSeedRootDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static hiberspring.constant.Constants.*;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final EmployeeCardService employeeCardService;
    private final BranchService branchService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser, EmployeeCardService employeeCardService, BranchService branchService) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.employeeCardService = employeeCardService;
        this.branchService = branchService;
    }


    @Override
    public Optional<Employee> getByNames(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Boolean employeesAreImported() {
        return repository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return FileUtil.read(Constants.FILE_EMPLOYEES);
    }

    @Override
    public String importEmployees() throws JAXBException, IOException {
        StringBuilder result = new StringBuilder();

        EmployeeSeedRootDto rootDto = xmlParser
                .unmarshalFromFile(FILE_EMPLOYEES, EmployeeSeedRootDto.class);

        if(rootDto == null){
            return (NOT_FOUND);
        }

        List<EmployeeSeedDto> dtos = rootDto.getEmployees();

        if(dtos == null || dtos.size() == 0){
            return (NOT_FOUND);
        }

        for (EmployeeSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)){

                if(getByNames(dto.getFirstName(), dto.getLastName()).isEmpty()){

                    Employee employee = mapper.map(dto, Employee.class);

                    EmployeeCard card = employeeCardService.getByNumber(dto.getCardNumber()).orElse(null);
                    Branch branch = branchService.getByName(dto.getBranchName()).orElse(null);

                    if(branch != null && card != null && !repository.existsEmployeeByCard(card)){

                        employee.setCard(card);
                        employee.setBranch(branch);
                        repository.saveAndFlush(employee);

                        result.append(String.format(SUCCESSFUL_IMPORT_MESSAGE,
                                "Employee",
                                String.format("%s %s",
                                        employee.getFirstName(),
                                        employee.getLastName())
                                )
                        );

                    } else {
                        result.append(INCORRECT_DATA_MESSAGE);
                    }
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

    @Override
    public String exportProductiveEmployees()  {
        StringBuilder result = new StringBuilder();

        List<Employee> employees = repository.getAllSortedByFullNameAndPositionLength();

        if(employees.size() == 0){
            return NOT_FOUND;
        }

        for (Employee employee: employees) {

            result.append(String.format(
                    "Name: %s %s%nPosition: %s%nCard Number: %s%n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getPosition(),
                    employee.getCard().getNumber()))
            .append("-------------------------")
            .append(System.lineSeparator());
        }
        return result.toString();
    }
}

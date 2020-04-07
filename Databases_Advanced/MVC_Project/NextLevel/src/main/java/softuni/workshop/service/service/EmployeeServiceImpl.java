package softuni.workshop.service.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.constant.Constants;
import softuni.workshop.data.dto.EmployeeSeedDto;
import softuni.workshop.data.dto.EmployeeSeedRootDto;
import softuni.workshop.data.entity.Employee;
import softuni.workshop.data.entity.Project;
import softuni.workshop.data.repository.EmployeeRepository;
import softuni.workshop.service.model.EmployeeViewModel;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidationUtil;
import softuni.workshop.util.XmlParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final ProjectService projectService;
    private final Gson gson;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser, ProjectService projectService, Gson gson) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.projectService = projectService;
        this.gson = gson;
    }


    @Override
    public void importEmployees() {
        EmployeeSeedRootDto rootDto = xmlParser
                .unmarshalFromFile(Constants.FILE_PATH_EMPLOYEES, EmployeeSeedRootDto.class);

        if(rootDto == null){
            return;
        }

        Collection<EmployeeSeedDto> dtos = rootDto.getEmployees();


        if(dtos == null || dtos.size() == 0){
            return;
        }

        for (EmployeeSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)){

                if(!repository.existsByFistNameAndLastNameAndAge(
                        dto.getFistName(),
                        dto.getLastName(),
                        dto.getAge())) {

                    Project project = projectService
                            .getByName(dto.getProject().getName()).orElse(null);

                    if(project != null){

                        Employee employee = mapper.map(dto, Employee.class);
                        employee.setProject(project);
                        repository.saveAndFlush(employee);
                    }
                }
            }
        }
    }

    @Override
    public boolean areImported() {
       return repository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() {
        return FileUtil.read(Constants.FILE_PATH_EMPLOYEES);
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        return
        repository
                .findEmployeesByAgeGreaterThan(Constants.EXPORT_EMPLOYEES_WITH_AGE_ABOVE)
                .stream()
                .map(EmployeeViewModel::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String exportEmployeesToJson() {
        return gson.toJson(this.findAll());
    }

    @Override
    public Collection<EmployeeViewModel> findAll() {
        return
        repository.findAll()
                .stream()
                .map(employee -> mapper.map(employee, EmployeeViewModel.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

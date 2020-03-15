package com.springdata.advance_mapping_part_two;


import com.springdata.advance_mapping_part_two.data.entities.Demo;
import com.springdata.advance_mapping_part_two.service.dtos.DemoDto;
import com.springdata.advance_mapping_part_two.service.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class App implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Autowired
    public App(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        //        EmployeeCreateDto e1 = new EmployeeCreateDto("Pecata", "Geshev", new Date(), 3400,4);
////        EmployeeCreateDto e2 = new EmployeeCreateDto("Pesho", "Geshev", new Date(), 3400,1);
////        EmployeeCreateDto e3 = new EmployeeCreateDto("Stamat", "Geshev", new Date(), 3400,1);
//        employeeService.save(e1);
////        employeeService.save(e2);
////        employeeService.save(e3);
//
//        List<ManagerDto> managerDtoList = employeeService.findAllManagers();
//
//        System.out.println();
////        EmployeeCreateDto e2 = new EmployeeCreateDto("Dragan", "Petrov", new Date(), 3400,1);
////        EmployeeViewDto employeeViewDto = employeeService.findByFirstAndLastName("Ivan","Petrov");
//
//        for (ManagerDto managerDto : managerDtoList) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(String.format("%s %s - Employees count: %d",managerDto.getFirstName(),managerDto.getLastName(),managerDto.getEmployees().size()))
//            .append(System.lineSeparator());
//
//            for (EmployeeViewDto employee : managerDto.getEmployees()) {
//                sb.append(String.format("\t- %s %s %.2f",employee.getFirstName(), employee.getLastName(), employee.getSalary()))
//                .append(System.lineSeparator());
//            }
//            System.out.println(sb.toString());
        DemoDto demoDto = new DemoDto("2020-03-09", "good");
        Demo demo = modelMapper.map(demoDto,Demo.class);

        int b =7;
    }
}

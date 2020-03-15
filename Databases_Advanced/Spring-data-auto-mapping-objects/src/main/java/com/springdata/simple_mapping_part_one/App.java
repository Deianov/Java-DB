package com.springdata.simple_mapping_part_one;

import com.springdata.simple_mapping_part_one.services.dtos.EmployeeSeedDto;
import com.springdata.simple_mapping_part_one.services.dtos.EmployeeViewDto;
import com.springdata.simple_mapping_part_one.services.sevice.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class App implements CommandLineRunner {

    private final EmployeeService employeeService;

    @Autowired
    public App(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {

        // Save
        EmployeeSeedDto employeeSeedDto = new EmployeeSeedDto
                ("Ivan", "Ivanov", 1500, "Sofia");

        employeeService.save(employeeSeedDto);

        // Find
        EmployeeViewDto employeeViewDto = employeeService.findByNames("Ivan", "Ivanov");
    }
}

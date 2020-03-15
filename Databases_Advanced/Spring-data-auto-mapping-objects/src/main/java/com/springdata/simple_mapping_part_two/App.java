package com.springdata.simple_mapping_part_two;

import com.springdata.simple_mapping_part_two.services.dtos.EmployeeSeedDto;
import com.springdata.simple_mapping_part_two.services.sevice.CityService;
import com.springdata.simple_mapping_part_two.services.sevice.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class App implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final CityService cityService;

    @Autowired
    public App(EmployeeService employeeService, CityService cityService) {
        this.employeeService = employeeService;
        this.cityService = cityService;
    }

    @Override
    public void run(String... args) throws Exception {

        // seed cities
//        CitySeedDto city1 = new CitySeedDto("Sofia");
//        CitySeedDto city2 = new CitySeedDto("Plovdiv");
//        CitySeedDto city3 = new CitySeedDto("Varna");
//        cityService.save(city1);
//        cityService.save(city2);
//        cityService.save(city3);

        EmployeeSeedDto employeeSeedDto =
                new EmployeeSeedDto("Ivan","Ivanov", 1500, "Sofia");

        this.employeeService.save(employeeSeedDto);

        // 1:40

    }
}

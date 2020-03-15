package com.springdata.advance_mapping_part_one;

import com.springdata.advance_mapping_part_one.services.dtos.EmployeeViewDto;
import com.springdata.advance_mapping_part_one.services.sevice.CityService;
import com.springdata.advance_mapping_part_one.services.sevice.EmployeeService;
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

        String firstName = "Ivan";
        String lastName = "Ivanov";
        String cityName = "Sofia";

        // seed cities
        cityService.save(new CitySeedDto("Sofia"));
        cityService.save(new CitySeedDto("Plovdiv"));
        cityService.save(new CitySeedDto("Varna"));

        // seed employee
        EmployeeSeedDto seedDto =
                new EmployeeSeedDto(firstName,lastName, 1500, cityName);

        this.employeeService.save(seedDto);

        // get employee
        EmployeeViewDto viewDto = employeeService.findByNames(firstName, lastName);

        System.out.println();
    }
}

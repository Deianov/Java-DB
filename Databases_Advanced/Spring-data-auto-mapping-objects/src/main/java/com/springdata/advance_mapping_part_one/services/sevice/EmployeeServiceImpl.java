package com.springdata.advance_mapping_part_one.services.sevice;

import com.springdata.advance_mapping_part_one.data.entities.City;
import com.springdata.advance_mapping_part_one.data.entities.Employee;
import com.springdata.advance_mapping_part_one.data.repositories.EmployeeRepository;
import com.springdata.advance_mapping_part_one.services.dtos.CityDto;
import com.springdata.advance_mapping_part_one.services.dtos.EmployeeSeedDto;
import com.springdata.advance_mapping_part_one.services.dtos.EmployeeViewDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final CityService cityService;

    @Autowired
    public EmployeeServiceImpl(ModelMapper modelMapper, EmployeeRepository employeeRepository, CityService cityService) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.cityService = cityService;
    }

    @Override
    public void save(EmployeeSeedDto employeeSeedDto) {

        Employee employee = modelMapper
                .map(employeeSeedDto, Employee.class);

        CityDto cityDto = cityService
                .findByName(employeeSeedDto.getAddress());

        employee.setCity(modelMapper.map(cityDto, City.class));

        employeeRepository.save(employee);
    }

    @Override
    public EmployeeViewDto findByNames(String firstName, String lastName) {

        Employee employee = employeeRepository.findByFirstNameAndLastName(firstName, lastName);

        EmployeeViewDto employeeViewDto = modelMapper.map(employee, EmployeeViewDto.class);
        return employeeViewDto;
    }
}

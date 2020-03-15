package com.springdata.simple_mapping_part_two.services.sevice;

import com.springdata.simple_mapping_part_two.data.entities.City;
import com.springdata.simple_mapping_part_two.data.entities.Employee;
import com.springdata.simple_mapping_part_two.data.repositories.EmployeeRepository;
import com.springdata.simple_mapping_part_two.services.dtos.CityDto;
import com.springdata.simple_mapping_part_two.services.dtos.EmployeeSeedDto;
import com.springdata.simple_mapping_part_two.services.dtos.EmployeeViewDto;
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
                .findByName(employeeSeedDto.getCity());

        employee.setCity(modelMapper.map(cityDto, City.class));

        employeeRepository.save(employee);
    }

    @Override
    public EmployeeViewDto findByNames(String firstName, String lastName) {
        return modelMapper
                .map(employeeRepository.findByFirstNameAndLastName(firstName, lastName), EmployeeViewDto.class);
    }
}

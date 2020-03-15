package com.springdata.simple_mapping_part_one.services.sevice;

import com.springdata.simple_mapping_part_one.data.entities.Employee;
import com.springdata.simple_mapping_part_one.data.repositories.EmployeeRepository;
import com.springdata.simple_mapping_part_one.services.dtos.EmployeeSeedDto;
import com.springdata.simple_mapping_part_one.services.dtos.EmployeeViewDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void save(EmployeeSeedDto employeeSeedDto) {
        Employee employee = modelMapper.map(employeeSeedDto, Employee.class);
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeViewDto findByNames(String firstName, String lastName) {
        return modelMapper
                .map(employeeRepository.findByFirstNameAndLastName(firstName, lastName), EmployeeViewDto.class);
    }
}

package com.springdata.advance_mapping_part_two.service.services;

import com.springdata.advance_mapping_part_two.service.dtos.EmployeeCreateDto;
import com.springdata.advance_mapping_part_two.service.dtos.EmployeeViewDto;
import com.springdata.advance_mapping_part_two.service.dtos.ManagerDto;
import javassist.NotFoundException;

import java.util.List;

public interface EmployeeService {

    void save(EmployeeCreateDto employee) throws NotFoundException;

    EmployeeViewDto findByFirstAndLastName(String fn, String ln);

    List<ManagerDto> findAllManagers();
}

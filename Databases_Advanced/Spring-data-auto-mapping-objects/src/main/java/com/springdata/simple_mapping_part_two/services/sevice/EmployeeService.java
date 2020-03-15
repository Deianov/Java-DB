package com.springdata.simple_mapping_part_two.services.sevice;

import com.springdata.simple_mapping_part_two.services.dtos.EmployeeSeedDto;
import com.springdata.simple_mapping_part_two.services.dtos.EmployeeViewDto;

public interface EmployeeService {

    void save(EmployeeSeedDto employeeSeedDto);

    EmployeeViewDto findByNames(String firstName, String lastName);

}

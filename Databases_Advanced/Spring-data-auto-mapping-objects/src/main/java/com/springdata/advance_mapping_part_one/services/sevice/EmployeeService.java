package com.springdata.advance_mapping_part_one.services.sevice;

import com.springdata.advance_mapping_part_one.services.dtos.EmployeeSeedDto;
import com.springdata.advance_mapping_part_one.services.dtos.EmployeeViewDto;

public interface EmployeeService {

    void save(EmployeeSeedDto employeeSeedDto);

    EmployeeViewDto findByNames(String firstName, String lastName);

}

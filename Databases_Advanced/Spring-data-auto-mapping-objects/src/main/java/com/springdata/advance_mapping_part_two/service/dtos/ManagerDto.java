package com.springdata.advance_mapping_part_two.service.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ManagerDto {

    private long id;
    private String firstName;
    private String lastName;
    private List<EmployeeViewDto> employees;
}

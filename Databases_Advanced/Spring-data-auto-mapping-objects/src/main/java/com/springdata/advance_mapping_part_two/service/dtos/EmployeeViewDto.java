package com.springdata.advance_mapping_part_two.service.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EmployeeViewDto {

    private String firstName;
    private String lastName;
    private double salary;
}

package com.springdata.simple_mapping_part_two.services.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeViewDto {
    private String firstName;
    private double salary;
}

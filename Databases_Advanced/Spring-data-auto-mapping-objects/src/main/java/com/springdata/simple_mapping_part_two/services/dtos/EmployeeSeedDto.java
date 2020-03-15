package com.springdata.simple_mapping_part_two.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeSeedDto {
    private String firstName;
    private String lastName;
    private double salary;
    private String city;
}

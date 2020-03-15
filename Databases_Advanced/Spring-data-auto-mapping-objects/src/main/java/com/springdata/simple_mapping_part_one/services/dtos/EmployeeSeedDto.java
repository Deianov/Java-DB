package com.springdata.simple_mapping_part_one.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSeedDto {
    private String firstName;
    private String lastName;
    private double salary;
    private String address;
}

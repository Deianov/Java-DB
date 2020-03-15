package com.springdata.simple_mapping_part_two.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityDto {
    private long id;
    private String name;

    public CityDto(String name) {
        this.name = name;
    }
}

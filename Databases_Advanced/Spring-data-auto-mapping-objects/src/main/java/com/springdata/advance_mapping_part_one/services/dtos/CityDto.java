package com.springdata.advance_mapping_part_one.services.dtos;

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

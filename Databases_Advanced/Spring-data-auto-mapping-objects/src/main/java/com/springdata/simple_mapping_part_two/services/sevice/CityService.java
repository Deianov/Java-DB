package com.springdata.simple_mapping_part_two.services.sevice;

import com.springdata.simple_mapping_part_two.services.dtos.CityDto;
import com.springdata.simple_mapping_part_two.services.dtos.CitySeedDto;

public interface CityService {
    void save(CitySeedDto cityDto);

    CityDto findByName(String name);
}

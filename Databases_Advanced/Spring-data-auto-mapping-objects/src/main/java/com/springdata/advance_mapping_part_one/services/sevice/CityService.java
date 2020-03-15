package com.springdata.advance_mapping_part_one.services.sevice;

import com.springdata.advance_mapping_part_one.services.dtos.CityDto;
import com.springdata.advance_mapping_part_one.services.dtos.CitySeedDto;

public interface CityService {
    void save(CitySeedDto cityDto);

    CityDto findByName(String name);
}

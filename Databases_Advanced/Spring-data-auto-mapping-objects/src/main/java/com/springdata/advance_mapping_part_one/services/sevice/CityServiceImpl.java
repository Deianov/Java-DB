package com.springdata.advance_mapping_part_one.services.sevice;

import com.springdata.advance_mapping_part_one.data.entities.City;
import com.springdata.advance_mapping_part_one.data.repositories.CityRepository;
import com.springdata.advance_mapping_part_one.services.dtos.CityDto;
import com.springdata.advance_mapping_part_one.services.dtos.CitySeedDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(CitySeedDto cityDto) {

        this.cityRepository
                .save(modelMapper.map(cityDto, City.class));

    }

    @Override
    public CityDto findByName(String name) {
        return modelMapper
                .map(cityRepository.findByName(name), CityDto.class);
    }
}

package com.springdata.advance_mapping_part_two.config;

import com.springdata.advance_mapping_part_two.data.entities.Demo;
import com.springdata.advance_mapping_part_two.service.dtos.DemoDto;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, LocalDate> dateConverter = new AbstractConverter<>() {
            @Override
            protected LocalDate convert(String s) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(s, formatter);
            }
        };

        Converter<String, String> stringConverter = new AbstractConverter<>() {
            @Override
            protected String convert(String s) {
                return s == null ? null : s.toUpperCase();
            }
        };

        TypeMap<DemoDto, Demo> typeMap = modelMapper.createTypeMap(DemoDto.class, Demo.class);
        typeMap.addMapping(src -> src.getDate(), Demo::setDate);

//        PropertyMap<DemoDto, Demo> propertyMap = new PropertyMap<>() {
//            @Override
//            protected void configure() {
//                using(stringConverter).map(source.getTitle()).setTitle(null);
//                using(dateConverter).map(source.getDate()).setDate(null);
//            }
//        };

//        modelMapper.addConverter(dateConverter);
//        modelMapper.addConverter(stringConverter);
//        modelMapper.addMappings(propertyMap);
        return modelMapper;
    }
}
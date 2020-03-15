package com.springdata.advance_mapping_part_one.config;

import com.springdata.advance_mapping_part_one.data.entities.Employee;
import com.springdata.advance_mapping_part_one.services.dtos.EmployeeViewDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBinConfiguration {

    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        // variant 1 - PropertyMap (old)
//        PropertyMap<Employee, EmployeeViewDto> propertyMap = new PropertyMap<Employee, EmployeeViewDto>() {
//            @Override
//            protected void configure() {
//                map().setAddress(source.getCity().getName());
//            }
//        };
//        modelMapper.addMappings(propertyMap);

        // variant 2 - TypeMap (new)
        TypeMap<Employee, EmployeeViewDto> typeMap =
                modelMapper.createTypeMap(Employee.class, EmployeeViewDto.class);

        typeMap.addMappings(m -> m.map(s -> s.getCity().getName(), EmployeeViewDto::setAddress));

        return modelMapper;
    }
}

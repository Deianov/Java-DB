package com.softuni.springautomappingdemo.config;

import com.softuni.springautomappingdemo.utils.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public BufferedReader reader(){
        return new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    }

    @Bean
    public UserOutput userOutput(){
        return new UserOutputImpl();
    }

    @Bean
    public ValidationUtil validationUtil(){
        return new ValidationUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

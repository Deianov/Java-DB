package com.springdata.exercises.utils;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserOutput {

    public void print(List<String> lines){
        lines.forEach(System.out::println);
    }

    public void print(String[] lines){
        for (String line : lines) {
            System.out.println(line);
        }
    }

    public void print(String value){
        System.out.println(value);
    }

    public void print(Integer value){
        System.out.println(value);
    }
}

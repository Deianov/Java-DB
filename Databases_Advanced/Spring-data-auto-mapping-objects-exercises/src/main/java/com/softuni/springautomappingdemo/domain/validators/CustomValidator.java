package com.softuni.springautomappingdemo.domain.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public final class CustomValidator {

    public static LocalDate parseLocalDate(String input, String format, String regex){

        if (input == null || format == null || regex == null){
            return null;
        }

        if (!input.matches(regex)){
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(input, formatter);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}

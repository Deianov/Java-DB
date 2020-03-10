package com.springdata.exercises.utils;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UserInput {
    private final Scanner scanner;

    public UserInput() {
        this.scanner = new Scanner(System.in);
    }

    public String get(String print, String defaultValue){
        if (!print.isBlank()){
            System.out.printf("%s%s",
                    print,
                    defaultValue == null ? "%n" : String.format(" <%s> :%n", defaultValue));
        }
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue == null ? "" : defaultValue : input;
    }

    public String get(String print){
        return this.get(print, null);
    }
}


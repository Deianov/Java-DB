package com.softuni.springautomappingdemo.utils;

import org.springframework.stereotype.Component;

@Component
public class UserOutputImpl implements UserOutput {
    private final StringBuilder stringBuilder;
    private int count = 0;

    public UserOutputImpl() {
        this.stringBuilder = new StringBuilder();
    }

    @Override
    public void set(String... lines) {
        stringBuilder.setLength(0);
        count = 0;
        this.add(lines);
    }

    @Override
    public void add(String... lines) {
        for (String line : lines) {
            if(count++ > 0){
                stringBuilder.append(System.lineSeparator());
            }
            stringBuilder.append(line);
        }
    }

    @Override
    public void print(String... lines) {
        for (String line : lines) {
            System.out.println(line);
        }
    }

    @Override
    public String toString(){
        return stringBuilder.toString();
    }
}

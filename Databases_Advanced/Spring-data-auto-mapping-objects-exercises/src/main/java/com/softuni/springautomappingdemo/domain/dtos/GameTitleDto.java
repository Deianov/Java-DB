package com.softuni.springautomappingdemo.domain.dtos;

public class GameTitleDto {
    private String title;

    public GameTitleDto(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

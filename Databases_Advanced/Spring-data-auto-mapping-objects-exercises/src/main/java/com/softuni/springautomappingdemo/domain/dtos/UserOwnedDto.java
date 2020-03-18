package com.softuni.springautomappingdemo.domain.dtos;


public class UserOwnedDto {
    private String[] games;

    public UserOwnedDto() {
    }

    public String[] getGames() {
        return games;
    }

    public void setGames(String[] games) {
        this.games = games;
    }
}

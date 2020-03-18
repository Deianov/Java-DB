package com.softuni.springautomappingdemo.domain.dtos;

import java.math.BigDecimal;

public class GameBaseViewDto {
    private String title;
    private BigDecimal price;

    public GameBaseViewDto(){}
    public GameBaseViewDto(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

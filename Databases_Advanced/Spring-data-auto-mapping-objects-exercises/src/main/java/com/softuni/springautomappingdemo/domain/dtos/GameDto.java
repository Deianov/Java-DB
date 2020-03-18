package com.softuni.springautomappingdemo.domain.dtos;


import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.softuni.springautomappingdemo.constants.ValidationConstants.*;

public class GameDto {
    @Pattern(regexp = GAME_TITLE_REGEX, message = GAME_TITLE_BEGIN_WITH_UPPERCASE)
    @Size(min = 3, max = 100, message = GAME_TITLE_LENGTH)
    private String title;

    @Min(value = 0, message = GAME_PRICE_MESSAGE)
    private BigDecimal price;

    @Min(value = 0, message = GAME_SIZE_MESSAGE)
    private double size;

    @Pattern(regexp = GAME_TRAILER_REGEX, message = GAME_TRAILER_MESSAGE)
    private String trailer;

    @Pattern(regexp = GAME_URL_REGEX, message = GAME_URL_MESSAGE)
    private String image;

    @Size(min = 20, max = 1000, message = GAME_DESCRIPTION_MESSAGE)
    private String description;

    @Past
    private LocalDate releaseDate;

    public GameDto(){}

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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}

package com.softuni.springautomappingdemo.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {
    private String title;
    private String trailer;
    private String image;
    private double size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;
    private Set<User> users;
    private Set<User> userCarts;

    public Game(){
        this.users = new HashSet<>();
        this.userCarts = new HashSet<>();
    }

    @Column(unique = true, nullable = false)
    public String getTitle() {
        return title;
    }

    @Basic
    public String getTrailer() {
        return trailer;
    }

    @Basic
    public String getImage() {
        return image;
    }

    @Basic
    public double getSize() {
        return size;
    }

    @Basic
    public BigDecimal getPrice() {
        return price;
    }

    @Basic
    public String getDescription() {
        return description;
    }

    @Column(name = "release_date")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @ManyToMany(mappedBy = "games", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    public Set<User> getUsers() {
        return users;
    }

    @ManyToMany(mappedBy = "shoppingCart", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    public Set<User> getUserCarts() {
        return userCarts;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setSize(double size) {
        this.size = size;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    public void setUsers(Set<User> users) { this.users = users; }
    public void setUserCarts(Set<User> userCarts) { this.userCarts = userCarts; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return title.equals(game.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}

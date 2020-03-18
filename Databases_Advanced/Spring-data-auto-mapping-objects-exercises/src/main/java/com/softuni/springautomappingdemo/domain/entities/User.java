package com.softuni.springautomappingdemo.domain.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String email;
    private String password;
    private String fullName;
    private Role role;
    private Set<Game> games;
    private Set<Game> shoppingCart;

    public User(){
        this.games = new LinkedHashSet<>();
        this.shoppingCart = new LinkedHashSet<>();
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    @Basic
    public String getPassword() {
        return password;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_games",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    public Set<Game> getGames() {
        return games;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_cart_items",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    public Set<Game> getShoppingCart() {
        return shoppingCart;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public void setGames(Set<Game> games) { this.games = games; }
    public void setShoppingCart(Set<Game> shoppingCart) { this.shoppingCart = shoppingCart; }
}

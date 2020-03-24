package com.productshop.model.entity;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(length = 60)
    private String firstName;

    @Column(length = 60, nullable = false)
    private String lastName;

    @Column
    private Integer age;

    @ManyToMany()
    @JoinTable(name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id")
    )
    private Set<User> friends;

    @OneToMany(mappedBy = "buyer")
    private Set<Product> boughtProducts;

    @OneToMany(mappedBy = "seller")
    private Set<Product> soldProducts;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age; }

    public void setAge(Integer age) {
        this.age = age; }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<Product> getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(Set<Product> boughtProducts) {
        this.boughtProducts = boughtProducts;
    }

    public Set<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public void addFriend(User friend) {

        if (friend == null || this.getId() == friend.getId()) {
            return;
        }
        for (User f : this.friends) {
            if (f.getId() == friend.getId()) {
               return;
            }
        }
        this.friends.add(friend);
    }
}

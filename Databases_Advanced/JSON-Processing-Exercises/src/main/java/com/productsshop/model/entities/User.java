package com.productsshop.model.entities;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private int age;
    private Set<User> friends;
    private Set<Product> bought;
    private Set<Product> sold;


    @Column(length = 60)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(length = 60, nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @ManyToMany()
    @JoinTable(name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id")
    )
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @OneToMany(mappedBy = "buyer") // ,fetch = FetchType.EAGER
    public Set<Product> getBought() {
        return bought;
    }

    public void setBought(Set<Product> bought) {
        this.bought = bought;
    }

    @OneToMany(mappedBy = "seller") // ,fetch = FetchType.EAGER
    public Set<Product> getSold() {
        return sold;
    }

    public void setSold(Set<Product> sold) {
        this.sold = sold;
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

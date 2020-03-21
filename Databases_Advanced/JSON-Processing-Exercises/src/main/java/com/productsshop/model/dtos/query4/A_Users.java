package com.productsshop.model.dtos.query4;

import com.google.gson.annotations.Expose;

import java.util.List;

public class A_Users {

    @Expose
    private Integer usersCount;

    @Expose
    private List<BC_User> users;

    public A_Users(Integer usersCount, List<BC_User> users) {
        this.usersCount = usersCount;
        this.users = users;
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<BC_User> getUsers() {
        return users;
    }

    public void setUsers(List<BC_User> users) {
        this.users = users;
    }
}

package com.softuni.springautomappingdemo.domain.dtos;

import com.softuni.springautomappingdemo.domain.entities.Role;

public class UserLoggedDto {
    private String email;
    private String password;
    private String fullName;
    private Role role;

    public UserLoggedDto(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

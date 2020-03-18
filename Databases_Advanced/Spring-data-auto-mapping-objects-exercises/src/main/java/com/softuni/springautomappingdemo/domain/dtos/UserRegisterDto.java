package com.softuni.springautomappingdemo.domain.dtos;


import com.softuni.springautomappingdemo.domain.validators.Password;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.softuni.springautomappingdemo.constants.ValidationConstants.*;

public class UserRegisterDto {

    @Pattern(regexp = USER_EMAIL_REGEX, message = USER_EMAIL_MESSAGE)
    private String email;

//    @Pattern(regexp = USER_PASSWORD_REGEX, message = USER_PASSWORD_MESSAGE)
//    @Size(min = USER_PASSWORD_MIN_LENGTH,
//            message = USER_PASSWORD_LENGTH_MESSAGE
//    )
    @Password
    private String password;

    @NotNull(message = USER_NAME_NOT_NULL_MESSAGE)
    private String fullName;

    public UserRegisterDto(){}
    public UserRegisterDto(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }


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
}

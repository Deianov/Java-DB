package entities;

import annotations.Column;
import annotations.Entity;
import annotations.Id;
import annotations.Unique;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private int id;

    @Unique
    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private int age;

    @Column(name = "registration_date")
    private Date registrationDate;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public User(String username, String password, int age, Date registrationDate) {
        this.setUsername(username);
        this.setPassword(password);
        this.setAge(age);
        this.setRegistrationDate(registrationDate);
    }
    public User(){}

    private void setUsername(String username) {
        this.username = username;
    }
    private void setPassword(String password) {
        this.password = password;
    }
    private void setAge(int age) {
        this.age = age;
    }
    private void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationDate() {
        return new SimpleDateFormat(DATE_FORMAT).format(this.registrationDate);
    }
}

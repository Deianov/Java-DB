package softuni.workshop.service.model;


import java.util.Collection;

public class UserServiceModel {

    private long id;
    private String username;
    private String password;
    private String email;
    private Collection<RoleServiceModel> authorities;

    public UserServiceModel() { }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }
}

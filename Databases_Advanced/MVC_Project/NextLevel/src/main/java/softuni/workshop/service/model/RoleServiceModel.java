package softuni.workshop.service.model;

public class RoleServiceModel {

    private long id;
    private String authority;

    public RoleServiceModel() { }
    public RoleServiceModel(long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

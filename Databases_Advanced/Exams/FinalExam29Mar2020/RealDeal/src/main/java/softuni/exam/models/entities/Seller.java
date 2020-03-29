package softuni.exam.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity{

    private String firstName;
    private String lastName;
    private String email;
    private Rating rating;
    private String town;

    public Seller() { }

    @Column(name = "first_name", length = 20)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 20)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Column(nullable = false)
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}

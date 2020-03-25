package softuni.exam.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "teams")
public class Team  extends BaseEntity{

    private String name;
    private Picture picture;

    public Team() { }

    @Column(length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne // cascade = CascadeType.PERSIST, new Picture?
    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        return super.toString();
    }
}

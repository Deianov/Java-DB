package entities.demo;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends Person {
    @Basic
    private String speciality;

    @OneToMany(mappedBy = "teacher", targetEntity = Course.class)
    private Set<Course> courseSet;

    public Teacher(){}
}

package entities.demo;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends Person {
    @Basic
    private double grade;

    @ManyToMany(mappedBy = "studentSet", targetEntity = Course.class)
    private Set<Course> courseSet;

    public Student(){}
}

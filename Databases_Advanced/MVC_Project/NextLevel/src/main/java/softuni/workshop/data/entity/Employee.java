package softuni.workshop.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{

    private String fistName;
    private String lastName;
    private Integer age;
    private Project project;

    public Employee() { }


    @Column(name = "first_name", nullable = false)
    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(nullable = false)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ManyToOne
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

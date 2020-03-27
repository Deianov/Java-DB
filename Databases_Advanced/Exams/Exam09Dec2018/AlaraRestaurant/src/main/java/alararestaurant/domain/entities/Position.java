package alararestaurant.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity {

    private String name;
    private Collection<Employee> employees;

    public Position() { }
    public Position(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Column(nullable = false, length = 30, unique = true)
    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "position")
    public Collection<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Collection<Employee> employees) {
        this.employees = employees;
    }
}

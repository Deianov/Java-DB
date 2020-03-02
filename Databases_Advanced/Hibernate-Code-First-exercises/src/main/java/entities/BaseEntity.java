package entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    public BaseEntity(){}

    public long getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}

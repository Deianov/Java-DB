package softuni.workshop.data.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {

    private long id;

    public BaseEntity() { }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

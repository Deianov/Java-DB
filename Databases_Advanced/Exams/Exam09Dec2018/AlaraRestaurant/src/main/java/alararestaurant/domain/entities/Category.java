package alararestaurant.domain.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    private String name;
    private Collection<Item> items;

    public Category() { }
    public Category(String name) {
        this.name = name;
    }

    @Column(nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }
}

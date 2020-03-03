package demos.springdata.springdataintro.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(unique = true)
    private String name;

    @NonNull
    private int age;

    @OneToMany(mappedBy = "user", targetEntity = Account.class, cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();

    // Prevent from recursion (from Lombok) -> -> !!!

    // 1.) -> Generate -> equals() and hashCode() -> only by id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    // 2.) -> Generate -> toString() -> all without accounts
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}

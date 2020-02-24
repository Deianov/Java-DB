package demos.hibernate.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

// JPA and Lombok
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "birth_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate = new Date();
}

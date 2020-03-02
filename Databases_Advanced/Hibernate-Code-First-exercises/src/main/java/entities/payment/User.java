package entities.payment;

import entities.BaseEntityUUID;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "users")
public class User extends BaseEntityUUID {

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Basic
    private String email;

    @Column(length = 20, nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_billing_details",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "billing_detail_id", referencedColumnName = "id")
    )
    private Set<BillingDetail> details;

    public User(){}
}

package entities.payment;

import entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "billing_details")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BillingDetail extends BaseEntity {

    @OneToOne
    private User owner;

    public BillingDetail(){}
}

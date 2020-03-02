package entities.payment;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Card extends BillingDetail {
    @Column(length = 20)
    private String type;

    @Column(name = "expiration_month")
    private int expirationMonth;

    @Column(name = "expiration_year")
    private int expirationYear;

    public Card(){}
}

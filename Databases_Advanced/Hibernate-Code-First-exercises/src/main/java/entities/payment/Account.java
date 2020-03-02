package entities.payment;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Account extends BillingDetail{

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "swift_code", length = 20)
    private String swiftCode;

    public Account(){}
}

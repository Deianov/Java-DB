package com.cardealer.model.dto.query5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSalesViewRootDto {

    @XmlElement(name = "customer")
    private List<CustomerSalesViewDto> customers;

    public CustomerSalesViewRootDto() { }
    public CustomerSalesViewRootDto(List<CustomerSalesViewDto> customers) {
        this.customers = customers;
    }

    public List<CustomerSalesViewDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerSalesViewDto> customers) {
        this.customers = customers;
    }
}

package alararestaurant.domain.dtos;

import alararestaurant.adapters.LocalDateTimeAdapter;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderSeedDto {

    @XmlElement
    private String customer;

    @XmlElement
    private String employee;

    @XmlElement(name = "date-time")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime dateTime;

    @XmlElement
    private String type;

    @XmlElement(name = "items")
    private OrderSeedItemRootDto orderItems;

    public OrderSeedDto() {
    }

    @NotNull
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @NotNull
    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    @NotNull
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @NotNull
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NotNull
    public OrderSeedItemRootDto getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderSeedItemRootDto orderItems) {
        this.orderItems = orderItems;
    }
}

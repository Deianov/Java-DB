package alararestaurant.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderSeedRootDto {

    @XmlElement(name = "order")
    private Collection<OrderSeedDto> orders;

    public OrderSeedRootDto() {
    }

    public OrderSeedRootDto(Collection<OrderSeedDto> orders) {
        this.orders = orders;
    }

    public Collection<OrderSeedDto> getOrders() {
        return orders;
    }

    public void setOrders(Collection<OrderSeedDto> orders) {
        this.orders = orders;
    }
}

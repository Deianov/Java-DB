package alararestaurant.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderSeedItemRootDto {

    @XmlElement(name = "item")
    private Collection<OrderSeedItemDto> items;

    public OrderSeedItemRootDto() {
    }

    public Collection<OrderSeedItemDto> getItems() {
        return items;
    }

    public void setItems(Collection<OrderSeedItemDto> items) {
        this.items = items;
    }
}

package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerSeedRootDto {

    @XmlElement(name = "seller")
    Collection<SellerSeedDto> sellers;

    public SellerSeedRootDto() { }

    public SellerSeedRootDto(Collection<SellerSeedDto> sellers) {
        this.sellers = sellers;
    }

    public Collection<SellerSeedDto> getSellers() {
        return sellers;
    }

    public void setSellers(Collection<SellerSeedDto> sellers) {
        this.sellers = sellers;
    }
}

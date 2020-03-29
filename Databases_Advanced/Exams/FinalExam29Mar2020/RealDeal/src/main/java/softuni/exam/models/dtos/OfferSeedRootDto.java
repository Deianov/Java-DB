package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedRootDto {

    @XmlElement(name = "offer")
    private Collection<OfferSeedDto> offers;

    public OfferSeedRootDto() {
    }

    public OfferSeedRootDto(Collection<OfferSeedDto> offers) {
        this.offers = offers;
    }

    public Collection<OfferSeedDto> getOffers() {
        return offers;
    }

    public void setOffers(Collection<OfferSeedDto> offers) {
        this.offers = offers;
    }
}

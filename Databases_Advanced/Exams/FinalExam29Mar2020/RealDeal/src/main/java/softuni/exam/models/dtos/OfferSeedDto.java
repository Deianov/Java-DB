package softuni.exam.models.dtos;

import softuni.exam.adapters.LocalDateTimeAdapter;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Seller;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDto {

    @XmlElement
    private String description;

    @XmlElement
    private BigDecimal price;

    @XmlElement(name = "added-on")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime addedOn;

    @XmlElement(name = "has-gold-status")
    private boolean hasGoldStatus;

    @XmlElement
    private CarSeedIdDto car;

    @XmlElement
    private SellerSeedIdDto seller;

    public OfferSeedDto() {
    }

    @Size(min = 5)
    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DecimalMin(value = "0")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public void setHasGoldStatus(boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    public CarSeedIdDto getCar() {
        return car;
    }

    public void setCar(CarSeedIdDto car) {
        this.car = car;
    }

    public SellerSeedIdDto getSeller() {
        return seller;
    }

    public void setSeller(SellerSeedIdDto seller) {
        this.seller = seller;
    }
}

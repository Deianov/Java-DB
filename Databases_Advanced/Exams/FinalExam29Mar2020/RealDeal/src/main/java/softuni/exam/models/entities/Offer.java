package softuni.exam.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    private BigDecimal price;
    private String description;
    private boolean hasGoldStatus;
    private LocalDateTime addedOn;
    private Car car;
    private Seller seller;
    private Collection<Picture> pictures;

    public Offer() { }

    @Column
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "has_gold_status ")
    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public void setHasGoldStatus(boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    @Column(nullable = false, name = "added_on")
    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    @ManyToOne
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @ManyToMany
    public Collection<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Collection<Picture> pictures) {
        this.pictures = pictures;
    }
}

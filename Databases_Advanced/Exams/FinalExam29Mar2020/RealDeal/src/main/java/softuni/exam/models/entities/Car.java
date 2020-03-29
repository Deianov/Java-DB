package softuni.exam.models.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity{

    private String make;
    private String model;
    private int kilometers;
    private LocalDate registeredOn;
    private Collection<Picture> pictures;

    public Car() { }

    @Column(length = 20, nullable = false)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column(length = 20, nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(nullable = false)
    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    @Column(name = "registered_on")
    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    public Collection<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Collection<Picture> pictures) {
        this.pictures = pictures;
    }
}

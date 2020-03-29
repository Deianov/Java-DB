package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class CarSeedDto {

    @Expose
    private String make;

    @Expose
    private String model;

    @Expose
    private int kilometers;

    @Expose
    private String registeredOn;

    public CarSeedDto() { }

    @Size(min = 2, max = 20)
    @NotNull
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Size(min = 2, max = 20)
    @NotNull
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Min(value = 0)
    @NotNull
    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }
}

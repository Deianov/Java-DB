package softuni.exam.models.dtos;


import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PictureSeedDto {

    @Expose
    private String name;

    @Expose
    private String dateAndTime;

    @Expose
    private Long car;

    public PictureSeedDto() { }

    @Size(min = 2, max = 20)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    @NotNull
    public Long getCar() {
        return car;
    }

    public void setCar(Long car) {
        this.car = car;
    }
}

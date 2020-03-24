package com.cardealer.model.dto.query4;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarAndPartsViewDto {

    @XmlAttribute
    private String make;

    @XmlAttribute
    private String model;

    @XmlAttribute(name = "travelled-distance")
    private Integer travelledDistance;

    @XmlElementWrapper(name = "parts")
    private List<PartViewDto> parts;

    public CarAndPartsViewDto() { }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Integer travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<PartViewDto> getParts() {
        return parts;
    }

    public void setParts(List<PartViewDto> parts) {
        this.parts = parts;
    }
}

package com.cardealer.model.dto.query4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarAndPartsViewRootDto {

    @XmlElement(name = "car")
    private List<CarAndPartsViewDto> parts;

    public CarAndPartsViewRootDto() { }
    public CarAndPartsViewRootDto(List<CarAndPartsViewDto> parts) {
        this.parts = parts;
    }
}

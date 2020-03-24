package com.cardealer.model.dto.query3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierAndPartsCountRootViewDto {

    @XmlElement(name = "supplier")
    private List<SupplierAndPartsCountViewDto> suppliers;

    public SupplierAndPartsCountRootViewDto() { }
    public SupplierAndPartsCountRootViewDto(List<SupplierAndPartsCountViewDto> suppliers) {
        this.suppliers = suppliers;
    }

    public List<SupplierAndPartsCountViewDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierAndPartsCountViewDto> suppliers) {
        this.suppliers = suppliers;
    }
}

package softuni.workshop.data.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeSeedRootDto {

    @XmlElement(name = "employee")
    private Collection<EmployeeSeedDto> employees;

    public EmployeeSeedRootDto() { }


    public Collection<EmployeeSeedDto> getEmployees() {
        return employees;
    }

    public void setEmployees(Collection<EmployeeSeedDto> employees) {
        this.employees = employees;
    }
}

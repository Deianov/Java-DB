package softuni.workshop.data.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanySeedRootDto {

    @XmlElement(name = "company")
    private Collection<CompanySeedDto> companies;

    public CompanySeedRootDto() { }


    public Collection<CompanySeedDto> getCompanies() {
        return companies;
    }

    public void setCompanies(Collection<CompanySeedDto> companies) {
        this.companies = companies;
    }
}

package softuni.workshop.data.dto;


import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeSeedDto {

    @XmlElement(name = "first-name")
    private String fistName;

    @XmlElement(name = "last-name")
    private String lastName;

    @XmlElement
    private Integer age;

    @XmlElement(name = "project")
    private ProjectSeedDto project;

    public EmployeeSeedDto() { }

    @NotNull
    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    @NotNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @NotNull
    public ProjectSeedDto getProject() {
        return project;
    }

    public void setProject(ProjectSeedDto project) {
        this.project = project;
    }
}

package softuni.workshop.data.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectSeedRootDto {

    @XmlElement(name = "project")
    private Collection<ProjectSeedDto> projects;

    public ProjectSeedRootDto() { }


    public Collection<ProjectSeedDto> getProjects() {
        return projects;
    }

    public void setProjects(Collection<ProjectSeedDto> projects) {
        this.projects = projects;
    }
}

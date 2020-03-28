package softuni.exam.domain.dto;


import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamSeedDto {

    @Expose
    @XmlElement
    private String name;

    @Expose
    @XmlElement
    private PictureSeedDto picture;

    public TeamSeedDto() { }

    @NotNull
    @Size(min = 3, max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public PictureSeedDto getPicture() {
        return picture;
    }

    public void setPicture(PictureSeedDto picture) {
        this.picture = picture;
    }
}

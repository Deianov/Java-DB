package entities.hospital;

import javax.persistence.*;

@Entity
@Table(name = "patients")
public class Patient extends Person {
    @Basic
    private String picture;

    @Basic
    private boolean insured;

    public Patient(){ }
    public Patient(String firstName, String lastName) {
        this.setInsured(false);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isInsured() {
        return insured;
    }

    public void setInsured(boolean insured) {
        this.insured = insured;
    }
}

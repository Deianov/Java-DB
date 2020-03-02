package entities.hospital;


import entities.BaseEntity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "prescriptions")
public class Prescription extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "visitation_id", referencedColumnName = "id")
    private Visitation visitation;

    @OneToOne
    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
    private Diagnose diagnose;

    @ManyToMany
    @JoinTable(name = "prescriptions_medicaments",
            joinColumns = @JoinColumn(name = "perscription_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id")
    )
    private Set<Medicament> medicaments;

    public Prescription(){
        this.medicaments = new LinkedHashSet<>();
    }
    public Prescription(Visitation visitation) {
        this();
        this.visitation = visitation;
        this.visitation.setPrescription(this);
    }

    public Visitation getVisitation() {
        return visitation;
    }

    public void setVisitation(Visitation visitation) {
        this.visitation = visitation;
    }

    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }

    public Set<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
    }

    public void addMedicament(Medicament medicament){
        this.medicaments.add(medicament);
    }
}

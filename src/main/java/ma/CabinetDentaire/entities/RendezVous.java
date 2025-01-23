package ma.CabinetDentaire.entities;

import ma.CabinetDentaire.entities.enums.TypeRDV;

import java.time.LocalDate;
import java.time.LocalTime;

public class RendezVous {
    private Long id;
    private String motif;
    private LocalTime temps;
    private DossierMedicale dossier;
    private Consultation consultation;
    private TypeRDV typeRDV;
    private LocalDate dateRDV;

    public RendezVous() {}
    public RendezVous(Long id, String motif, LocalTime temps, DossierMedicale dossier, Consultation consultation, TypeRDV typeRDV, LocalDate dateRDV) {
        this.id = id;
        this.motif = motif;
        this.temps = temps;
        this.dossier = dossier;
        this.consultation = consultation;
        this.typeRDV = typeRDV;
        this.dateRDV = dateRDV;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public LocalTime getTemps() {
        return temps;
    }

    public void setTemps(LocalTime temps) {
        this.temps = temps;
    }

    public DossierMedicale getDossier() {
        return dossier;
    }

    public void setDossier(DossierMedicale dossier) {
        this.dossier = dossier;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public TypeRDV getTypeRDV() {
        return typeRDV;
    }

    public void setTypeRDV(TypeRDV typeRDV) {
        this.typeRDV = typeRDV;
    }

    public LocalDate getDateRDV() {
        return dateRDV;
    }

    public void setDateRDV(LocalDate dateRDV) {
        this.dateRDV = dateRDV;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", motif='" + motif + '\'' +
                ", temps=" + temps +
                ", dossier=" + dossier +
                ", consultation=" + consultation +
                ", typeRDV=" + typeRDV +
                ", dateRDV=" + dateRDV +
                '}';
    }
}

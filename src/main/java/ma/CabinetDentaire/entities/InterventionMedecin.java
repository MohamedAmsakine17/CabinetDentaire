package ma.CabinetDentaire.entities;

public class InterventionMedecin {
    private Long id;
    private Long dent;
    private Double prixPatient;
    private String noteMedecin;
    private Consultation consultation;
    private Acte acte;

    public InterventionMedecin() {}
    public InterventionMedecin(Long id, Long dent, Double prixPatient, String noteMedecin, Consultation consultation, Acte acte) {
        this.id = id;
        this.dent = dent;
        this.prixPatient = prixPatient;
        this.noteMedecin = noteMedecin;
        this.consultation = consultation;
        this.acte = acte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDent() {
        return dent;
    }

    public void setDent(Long dent) {
        this.dent = dent;
    }

    public Double getPrixPatient() {
        return prixPatient;
    }

    public void setPrixPatient(Double prixPatient) {
        this.prixPatient = prixPatient;
    }

    public String getNoteMedecin() {
        return noteMedecin;
    }

    public void setNoteMedecin(String noteMedecin) {
        this.noteMedecin = noteMedecin;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public Acte getActe() {
        return acte;
    }

    public void setActe(Acte acte) {
        this.acte = acte;
    }
}

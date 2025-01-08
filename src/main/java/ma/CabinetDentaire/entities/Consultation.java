package ma.CabinetDentaire.entities;

import ma.CabinetDentaire.entities.enums.TypeConsultation;

import java.time.LocalDate;
import java.util.List;

public class Consultation {
    private Long idConsultation;
    private TypeConsultation typeConsultation;
    private LocalDate dateConsultation;
    private Ordonnance ordonnance;
    private DossierMedicale dossierMedicale;
    private List<Facture> factures;
    private List<InterventionMedecin> interventions;

    public Consultation() {}
    public Consultation(Long idConsultation, TypeConsultation typeConsultation, LocalDate dateConsultation, Ordonnance ordonnance, DossierMedicale dossierMedicale) {
        this.idConsultation = idConsultation;
        this.typeConsultation = typeConsultation;
        this.dateConsultation = dateConsultation;
        this.ordonnance = ordonnance;
        this.dossierMedicale = dossierMedicale;
    }

    public Long getIdConsultation() {
        return idConsultation;
    }

    public void setIdConsultation(Long idConsultation) {
        this.idConsultation = idConsultation;
    }

    public TypeConsultation getTypeConsultation() {
        return typeConsultation;
    }

    public void setTypeConsultation(TypeConsultation typeConsultation) {
        this.typeConsultation = typeConsultation;
    }

    public LocalDate getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(LocalDate dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public Ordonnance getOrdonnance() {
        return ordonnance;
    }

    public void setOrdonnance(Ordonnance ordonnance) {
        this.ordonnance = ordonnance;
    }

    public DossierMedicale getDossierMedicale() {
        return dossierMedicale;
    }

    public void setDossierMedicale(DossierMedicale dossierMedicale) {
        this.dossierMedicale = dossierMedicale;
    }

    public List<Facture> getFactures() {
        return factures;
    }

    public List<InterventionMedecin> getInterventions() {
        return interventions;
    }

    public void addFacture(Facture facture) {
        factures.add(facture);
    }

    public void addIntervention(InterventionMedecin interventionMedecin) {
        interventions.add(interventionMedecin);
    }
}

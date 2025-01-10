package ma.CabinetDentaire.entities;

import ma.CabinetDentaire.entities.enums.StatutPaiment;

import java.time.LocalDate;
import java.util.List;

public class DossierMedicale {
    private Long id;
    private Patient patient;
    private String numeroDossier;
    private LocalDate dateCreation;
    private Dentiste dentiste;
    private StatutPaiment statutPaiment;
    private SituationFinanciere situationFinanciere;
    private List<RendezVous> rdvs;
    private List<Ordonnance> ordonnances;
    private List<Consultation> consultations;
    // ID|PATIENT_ID|NUMERO_DOSSIER|DATE_CREATION|DENTIST_ID|Statut_Paiment
    public DossierMedicale() {}
    public DossierMedicale(Long id, Patient patient, String numeroDossier, LocalDate dateCreation, Dentiste dentiste, StatutPaiment statutPaiment, SituationFinanciere situationFinanciere) {
        this.id = id;
        this.patient = patient;
        this.numeroDossier = numeroDossier;
        this.dateCreation = dateCreation;
        this.dentiste = dentiste;
        this.statutPaiment = statutPaiment;
        this.situationFinanciere = situationFinanciere;
    }
    public DossierMedicale(Long id, Patient patient, String numeroDossier, LocalDate dateCreation, Dentiste dentiste, StatutPaiment statutPaiment) {
        this.id = id;
        this.patient = patient;
        this.numeroDossier = numeroDossier;
        this.dateCreation = dateCreation;
        this.dentiste = dentiste;
        this.statutPaiment = statutPaiment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getNumeroDossier() {
        return numeroDossier;
    }

    public void setNumeroDossier(String numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Dentiste getDentiste() {
        return dentiste;
    }

    public void setDentiste(Dentiste dentiste) {
        this.dentiste = dentiste;
    }

    public StatutPaiment getStatutPaiment() {
        return statutPaiment;
    }

    public void setStatutPaiment(StatutPaiment statutPaiment) {
        this.statutPaiment = statutPaiment;
    }

    public SituationFinanciere getSituationFinanciere() {
        return situationFinanciere;
    }

    public void setSituationFinanciere(SituationFinanciere situationFinanciere) {
        this.situationFinanciere = situationFinanciere;
    }

    public void setRdvs(List<RendezVous> rdvs) {
        this.rdvs = rdvs;
    }

    public void setOrdonnances(List<Ordonnance> ordonnances) {
        this.ordonnances = ordonnances;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public void addRdv(RendezVous rdv) {
        this.rdvs.add(rdv);
    }

    public void addOrdonnance(Ordonnance ordonnance) {
        this.ordonnances.add(ordonnance);
    }

    public void addConsultation(Consultation consultation) {
        this.consultations.add(consultation);
    }
}

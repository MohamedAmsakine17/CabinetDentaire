package ma.CabinetDentaire.entities;

import ma.CabinetDentaire.entities.enums.TypePaiement;

import java.time.LocalDate;

public class Facture {
    private Long id;
    private Double montantTotal;
    private Double montantRestant;
    private Double montantPaye;
    private LocalDate dataFacturation;
    private SituationFinanciere situationFinanciere;
    private TypePaiement typePaiement;
    private Consultation consultation;

    public Facture() {}
    public Facture(Long id, Double montantTotal, Double montantRestant, Double montantPaye, LocalDate dataFacturation, SituationFinanciere situationFinanciere, TypePaiement typePaiement, Consultation consultation) {
        this.id = id;
        this.montantTotal = montantTotal;
        this.montantRestant = montantRestant;
        this.montantPaye = montantPaye;
        this.dataFacturation = dataFacturation;
        this.situationFinanciere = situationFinanciere;
        this.typePaiement = typePaiement;
        this.consultation = consultation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Double getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(Double montantRestant) {
        this.montantRestant = montantRestant;
    }

    public Double getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(Double montantPaye) {
        this.montantPaye = montantPaye;
    }

    public LocalDate getDataFacturation() {
        return dataFacturation;
    }

    public void setDataFacturation(LocalDate dataFacturation) {
        this.dataFacturation = dataFacturation;
    }

    public SituationFinanciere getSituationFinanciere() {
        return situationFinanciere;
    }

    public void setSituationFinanciere(SituationFinanciere situationFinanciere) {
        this.situationFinanciere = situationFinanciere;
    }

    public TypePaiement getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(TypePaiement typePaiement) {
        this.typePaiement = typePaiement;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }
}

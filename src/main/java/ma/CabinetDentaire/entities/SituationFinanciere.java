package ma.CabinetDentaire.entities;

import java.time.LocalDate;
import java.util.List;

public class SituationFinanciere {
    private Long id;
    private Double montantGlobalePaye;
    private Double montantGlobaleRestant;
    private LocalDate dateCreation;
    private DossierMedicale dossierMedicale;
    private List<Facture> factures;

    public SituationFinanciere() {}

    public SituationFinanciere(Long id, Double montantGlobalePaye, Double montantGlobaleRestant, LocalDate dateCreation, DossierMedicale dossierMedicale) {
        this.id = id;
        this.montantGlobalePaye = montantGlobalePaye;
        this.montantGlobaleRestant = montantGlobaleRestant;
        this.dateCreation = dateCreation;
        this.dossierMedicale = dossierMedicale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontantGlobalePaye() {
        return montantGlobalePaye;
    }

    public void setMontantGlobalePaye(Double montantGlobalePaye) {
        this.montantGlobalePaye = montantGlobalePaye;
    }

    public Double getMontantGlobaleRestant() {
        return montantGlobaleRestant;
    }

    public void setMontantGlobaleRestant(Double montantGlobaleRestant) {
        this.montantGlobaleRestant = montantGlobaleRestant;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
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

    public void addFacture(Facture facture) {
        this.factures.add(facture);
    }
}

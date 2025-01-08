package ma.CabinetDentaire.entities;

import ma.CabinetDentaire.entities.enums.CategorieActe;
import java.util.List;

public class Acte {
    private Long id;
    private String libelle;
    private Double prixDeBase;
    private CategorieActe categorieActe;
    private List<InterventionMedecin> interventions;

    public Acte() {}
    public Acte(Long id, String libelle, Double prixDeBase, CategorieActe categorieActe) {
        this.id = id;
        this.libelle = libelle;
        this.prixDeBase = prixDeBase;
        this.categorieActe = categorieActe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrixDeBase() {
        return prixDeBase;
    }

    public void setPrixDeBase(Double prixDeBase) {
        this.prixDeBase = prixDeBase;
    }

    public CategorieActe getCategorieActe() {
        return categorieActe;
    }

    public void setCategorieActe(CategorieActe categorieActe) {
        this.categorieActe = categorieActe;
    }

    public List<InterventionMedecin> getInterventions() {
        return interventions;
    }

    public void addIntervention(InterventionMedecin intervention) {
        interventions.add(intervention);
    }
}

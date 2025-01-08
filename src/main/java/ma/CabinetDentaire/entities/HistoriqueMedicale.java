package ma.CabinetDentaire.entities;

import ma.CabinetDentaire.entities.enums.CategorieHistoriqueMedicale;

import java.util.List;

public class HistoriqueMedicale {
    private Long idAntecedent;
    private String libelle;
    CategorieHistoriqueMedicale categorie;
    List<Patient> patients;

    public HistoriqueMedicale() {}
    public HistoriqueMedicale(Long idAntecedent, String libelle, CategorieHistoriqueMedicale categorie) {
        this.idAntecedent = idAntecedent;
        this.libelle = libelle;
        this.categorie = categorie;
    }

    public Long getIdAntecedent() { return idAntecedent; }
    public String getLibelle() { return libelle; }
    public CategorieHistoriqueMedicale getCategorie() { return categorie; }
    public List<Patient> getPatients() { return patients; }

    public void setIdAntecedent(Long idAntecedent) { this.idAntecedent = idAntecedent; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public void setCategorie(CategorieHistoriqueMedicale categorie) { this.categorie = categorie; }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }
}

package ma.CabinetDentaire.entities;

import java.util.List;

public class Medicament {
    private Long idMedicament;
    private double prix;
    private String nom;
    private String description;
    private List<HistoriqueMedicale> precautionsEnCasDe;
    private List<PrescriptionDeMedicament> prescriptionDeMedicaments;

    public Medicament() {}
    public Medicament(Long idMedicament, double prix, String nom, String description) {
        this.idMedicament = idMedicament;
        this.prix = prix;
        this.nom = nom;
        this.description = description;
    }

    public Long getIdMedicament() { return idMedicament; }
    public double getPrix() { return prix; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public List<PrescriptionDeMedicament> getPrescriptionDeMedicaments() { return prescriptionDeMedicaments; }
    public List<HistoriqueMedicale> getPrecautionsEnCasDe() { return precautionsEnCasDe; }

    public void setIdMedicament(Long idMedicament) { this.idMedicament = idMedicament; }
    public void setPrix(double prix) { this.prix = prix; }
    public void setNom(String nom) { this.nom = nom; }
    public void setDescription(String description) { this.description = description; }

    public void addPrecautionsEnCasDe(HistoriqueMedicale precautionsEnCasDe) {
        this.precautionsEnCasDe.add(precautionsEnCasDe);
    }

    public void addPrescriptionDeMedicaments(PrescriptionDeMedicament prescriptionDeMedicament) {
        this.prescriptionDeMedicaments.add(prescriptionDeMedicament);
    }
}

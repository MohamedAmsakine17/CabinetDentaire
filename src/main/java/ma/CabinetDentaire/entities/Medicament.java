package ma.CabinetDentaire.entities;

import java.util.List;

public class Medicament {
    private Long idMedicament;
    private double prix;
    private String nom;
    private String description;
    private String iamgeSrc;
    private List<HistoriqueMedicale> precautionsEnCasDe;
    private List<PrescriptionDeMedicament> prescriptionDeMedicaments;

    public Medicament() {}
    public Medicament(Long idMedicament, double prix, String nom, String description, String imgSrc) {
        this.idMedicament = idMedicament;
        this.prix = prix;
        this.nom = nom;
        this.description = description;
        this.iamgeSrc = imgSrc;
    }

    public Long getIdMedicament() { return idMedicament; }
    public double getPrix() { return prix; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public String getIamgeSrc() { return iamgeSrc; }
    public List<PrescriptionDeMedicament> getPrescriptionDeMedicaments() { return prescriptionDeMedicaments; }
    public List<HistoriqueMedicale> getPrecautionsEnCasDe() { return precautionsEnCasDe; }

    public void setIdMedicament(Long idMedicament) { this.idMedicament = idMedicament; }
    public void setPrix(double prix) { this.prix = prix; }
    public void setNom(String nom) { this.nom = nom; }
    public void setDescription(String description) { this.description = description; }
    public void setIamgeSrc(String iamgeSrc) { this.iamgeSrc = iamgeSrc; }

    public void addPrecautionsEnCasDe(HistoriqueMedicale precautionsEnCasDe) {
        this.precautionsEnCasDe.add(precautionsEnCasDe);
    }
    public void addPrescriptionDeMedicaments(PrescriptionDeMedicament prescriptionDeMedicament) {
        this.prescriptionDeMedicaments.add(prescriptionDeMedicament);
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "idMedicament=" + idMedicament +
                ", prix=" + prix +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", precautionsEnCasDe=" + precautionsEnCasDe +
                ", prescriptionDeMedicaments=" + prescriptionDeMedicaments +
                '}';
    }
}

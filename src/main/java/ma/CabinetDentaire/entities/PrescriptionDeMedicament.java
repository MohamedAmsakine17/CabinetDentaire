package ma.CabinetDentaire.entities;

public class PrescriptionDeMedicament {
    private Long idPrescription;
    private int unitesMinAPrendre;
    private int unitesMaxAPrendre;
    private String contraintesAlimentation;
    private Ordonnance ordonnance;
    private String contraintesTemps;
    private Medicament medicamentAPrescrire;

    public PrescriptionDeMedicament() {}
    public PrescriptionDeMedicament(Long idPrescription,int unitesMinAPrendre, int unitesMaxAPrendre, String contraintesAlimentation,
                                    Ordonnance ordonnance, String contraintesTemps, Medicament medicamentAPrescrire) {
        this.idPrescription = idPrescription;
        this.unitesMinAPrendre = unitesMinAPrendre;
        this.unitesMaxAPrendre = unitesMaxAPrendre;
        this.contraintesAlimentation = contraintesAlimentation;
        this.ordonnance = ordonnance;
        this.contraintesTemps = contraintesTemps;
        this.medicamentAPrescrire = medicamentAPrescrire;
    }

    public Long getIdPrescription() { return idPrescription; }
    public int getUnitesMinAPrendre() { return unitesMinAPrendre; }
    public int getUnitesMaxAPrendre() { return unitesMaxAPrendre; }
    public String getContraintesAlimentation() { return contraintesAlimentation; }
    public Ordonnance getOrdonnance() { return ordonnance; }
    public String getContraintesTemps() { return contraintesTemps; }
    public Medicament getMedicamentAPrescrire() { return medicamentAPrescrire; }

    public void setIdPrescription(Long idPrescription) { this.idPrescription = idPrescription; }
    public void setUnitesMinAPrendre(int unitesMinAPrendre) { this.unitesMinAPrendre = unitesMinAPrendre;}
    public void setUnitesMaxAPrendre(int unitesMaxAPrendre) { this.unitesMaxAPrendre = unitesMaxAPrendre;}
    public void setContraintesAlimentation(String contraintesAlimentation) { this.contraintesAlimentation = contraintesAlimentation;}
    public void setOrdonnance(Ordonnance ordonnance) { this.ordonnance = ordonnance;}
    public void setContraintesTemps(String contraintesTemps) { this.contraintesTemps = contraintesTemps; }
    public void setMedicamentAPrescrire(Medicament medicamentAPrescrire) { this.medicamentAPrescrire = medicamentAPrescrire;}
}

package ma.CabinetDentaire.entities;

import java.time.LocalDate;
import java.util.List;

public class Ordonnance {
    private Long idOrdonnance;
    private LocalDate date;
    private Consultation consultationConcernee;
    private List<PrescriptionDeMedicament> prescriptionDeMedicament;

    public Ordonnance() {}
    public Ordonnance(Long idOrdonnance, LocalDate date, Consultation consultation){
        this.idOrdonnance = idOrdonnance;
        this.date = date;
        this.consultationConcernee = consultation;
    }

    public Long getIdOrdonnance() {
        return idOrdonnance;
    }

    public void setIdOrdonnance(Long idOrdonnance) {
        this.idOrdonnance = idOrdonnance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Consultation getConsultationConcernee() {
        return consultationConcernee;
    }

    public void setConsultationConcernee(Consultation consultationConcernee) {
        this.consultationConcernee = consultationConcernee;
    }

    public List<PrescriptionDeMedicament> getPrescriptionDeMedicament() {
        return prescriptionDeMedicament;
    }

    public void addPrescriptionDeMedicament(PrescriptionDeMedicament prescription) {
        prescriptionDeMedicament.add(prescription);
    }
}

package ma.CabinetDentaire.service.api;

import ma.CabinetDentaire.entities.PrescriptionDeMedicament;
import ma.CabinetDentaire.service.exceptions.PrescriptionDeMedicamentException;

import java.util.List;

public interface IPrescriptionDeMedicamentService {
    PrescriptionDeMedicament createPrescription(int unitesMin, int unitesMax, String contraintesAlimentation, Long ordonnanceId, String contraintesTemps, Long medicamentId) throws PrescriptionDeMedicamentException;
    PrescriptionDeMedicament findById(Long id) throws PrescriptionDeMedicamentException;
    List<PrescriptionDeMedicament> findAll() throws PrescriptionDeMedicamentException;
    List<PrescriptionDeMedicament> findByOrdonnance(Long ordonnanceId) throws PrescriptionDeMedicamentException;
    void updatePrescription(PrescriptionDeMedicament prescription) throws PrescriptionDeMedicamentException;
    void deletePrescriptionById(Long id) throws PrescriptionDeMedicamentException;
}
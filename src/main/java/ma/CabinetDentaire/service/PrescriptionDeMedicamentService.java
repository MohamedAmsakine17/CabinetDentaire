package ma.CabinetDentaire.service;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.PrescriptionDeMedicament;
import ma.CabinetDentaire.repository.api.IPrescriptionDeMedicamentRepo;
import ma.CabinetDentaire.service.api.IPrescriptionDeMedicamentService;
import ma.CabinetDentaire.service.exceptions.PrescriptionDeMedicamentException;

import java.util.List;

public class PrescriptionDeMedicamentService implements IPrescriptionDeMedicamentService {
    private final IPrescriptionDeMedicamentRepo prescriptionRepo;

    public PrescriptionDeMedicamentService(IPrescriptionDeMedicamentRepo prescriptionRepo) {
        this.prescriptionRepo = prescriptionRepo;
    }

    @Override
    public PrescriptionDeMedicament createPrescription(int unitesMin, int unitesMax, String contraintesAlimentation, Long ordonnanceId, String contraintesTemps, Long medicamentId) throws PrescriptionDeMedicamentException {
        try {
            PrescriptionDeMedicament prescription = new PrescriptionDeMedicament(
                    null, unitesMin, unitesMax, contraintesAlimentation,
                    AppFactory.getOrdonnanceRepo().findById(ordonnanceId),
                    contraintesTemps,
                    AppFactory.getMedicamentRepo().findById(medicamentId)
            );
            return prescriptionRepo.save(prescription);
        } catch (Exception e) {
            throw new PrescriptionDeMedicamentException("Error creating PrescriptionDeMedicament: " + e.getMessage());
        }
    }

    @Override
    public PrescriptionDeMedicament findById(Long id) throws PrescriptionDeMedicamentException {
        try {
            return prescriptionRepo.findById(id);
        } catch (Exception e) {
            throw new PrescriptionDeMedicamentException("Error finding PrescriptionDeMedicament: " + e.getMessage());
        }
    }

    @Override
    public List<PrescriptionDeMedicament> findAll() throws PrescriptionDeMedicamentException {
        try {
            return prescriptionRepo.findAll();
        } catch (Exception e) {
            throw new PrescriptionDeMedicamentException("Error fetching all PrescriptionDeMedicaments: " + e.getMessage());
        }
    }

    @Override
    public List<PrescriptionDeMedicament> findByOrdonnance(Long ordonnanceId) throws PrescriptionDeMedicamentException {
        try {
            return prescriptionRepo.findByOrdonnance(ordonnanceId);
        } catch (Exception e) {
            throw new PrescriptionDeMedicamentException("Error fetching PrescriptionDeMedicaments by Ordonnance: " + e.getMessage());
        }
    }

    @Override
    public void updatePrescription(PrescriptionDeMedicament prescription) throws PrescriptionDeMedicamentException {
        try {
            prescriptionRepo.update(prescription);
        } catch (Exception e) {
            throw new PrescriptionDeMedicamentException("Error updating PrescriptionDeMedicament: " + e.getMessage());
        }
    }

    @Override
    public void deletePrescriptionById(Long id) throws PrescriptionDeMedicamentException {
        try {
            prescriptionRepo.deleteById(id);
        } catch (Exception e) {
            throw new PrescriptionDeMedicamentException("Error deleting PrescriptionDeMedicament: " + e.getMessage());
        }
    }
}
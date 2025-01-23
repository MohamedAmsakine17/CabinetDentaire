package ma.CabinetDentaire.service;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Ordonnance;
import ma.CabinetDentaire.repository.api.IOrdonnanceRepo;
import ma.CabinetDentaire.repository.api.IPrescriptionDeMedicamentRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.api.IOrdonnanceService;
import ma.CabinetDentaire.service.api.IPrescriptionDeMedicamentService;
import ma.CabinetDentaire.service.exceptions.OrdonnanceException;

import java.time.LocalDate;
import java.util.List;

public class OrdonnanceService implements IOrdonnanceService {
    private final IOrdonnanceRepo ordonnanceRepo;
    private final IPrescriptionDeMedicamentRepo prescriptionDeMedicamentRepo;

    public OrdonnanceService(IOrdonnanceRepo ordonnanceRepo, IPrescriptionDeMedicamentRepo prescriptionDeMedicamentRepo) {
        this.ordonnanceRepo = ordonnanceRepo;
        this.prescriptionDeMedicamentRepo = prescriptionDeMedicamentRepo;
    }

    @Override
    public Ordonnance createOrdonnance(Ordonnance ordonnance) throws OrdonnanceException {
        try {
            return ordonnanceRepo.save(ordonnance);
        } catch (Exception e) {
            throw new OrdonnanceException("Error creating Ordonnance: " + e.getMessage());
        }
    }

    @Override
    public Ordonnance findById(Long id) throws OrdonnanceException {
        try {
            return ordonnanceRepo.findById(id);
        } catch (Exception e) {
            throw new OrdonnanceException("Error finding Ordonnance: " + e.getMessage());
        }
    }

    @Override
    public List<Ordonnance> findAll() throws OrdonnanceException {
        try {
            return ordonnanceRepo.findAll();
        } catch (Exception e) {
            throw new OrdonnanceException("Error fetching all Ordonnances: " + e.getMessage());
        }
    }

    @Override
    public List<Ordonnance> findAllByDossier(Long id) throws OrdonnanceException {
        try {
            return ordonnanceRepo.findAllByDossierId(id);
        } catch (Exception e) {
            throw new OrdonnanceException("Error fetching all Ordonnances: " + e.getMessage());
        }
    }

    @Override
    public Ordonnance findByConsultation(Long consultationId) throws OrdonnanceException {
        try {
                return ordonnanceRepo.findByConsultation(consultationId);
        } catch (Exception e) {
            throw new OrdonnanceException("Error fetching Ordonnances by Consultation: " + e.getMessage());
        }
    }

    @Override
    public void updateOrdonnance(Ordonnance ordonnance) throws OrdonnanceException {
        try {
            ordonnanceRepo.update(ordonnance);
        } catch (Exception e) {
            throw new OrdonnanceException("Error updating Ordonnance: " + e.getMessage());
        }
    }

    @Override
    public void deleteOrdonnanceById(Long id) throws OrdonnanceException {
        try {
            prescriptionDeMedicamentRepo.findByOrdonnance(ordonnanceRepo.findById(id).getIdOrdonnance()).forEach(prescriptionDeMedicament -> {
                try {
                    prescriptionDeMedicamentRepo.deleteById(prescriptionDeMedicament.getIdPrescription());
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }
            });
            ordonnanceRepo.deleteById(id);
        } catch (Exception e) {
            throw new OrdonnanceException("Error deleting Ordonnance: " + e.getMessage());
        }
    }
}
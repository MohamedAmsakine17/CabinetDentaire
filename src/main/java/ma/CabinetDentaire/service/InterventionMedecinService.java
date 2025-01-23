package ma.CabinetDentaire.service;

import ma.CabinetDentaire.entities.InterventionMedecin;
import ma.CabinetDentaire.repository.api.IInterventionMedecinRepo;
import ma.CabinetDentaire.service.api.IInterventionMedecinService;
import ma.CabinetDentaire.service.exceptions.InterventionMedecinException;

import java.util.List;

public class InterventionMedecinService implements IInterventionMedecinService {
    private final IInterventionMedecinRepo interventionRepo;

    public InterventionMedecinService(IInterventionMedecinRepo interventionRepo) {
        this.interventionRepo = interventionRepo;
    }

    @Override
    public InterventionMedecin createIntervention(InterventionMedecin intervention) throws InterventionMedecinException {
        try {
            return interventionRepo.save(intervention);
        } catch (Exception e) {
            throw new InterventionMedecinException("Error creating InterventionMedecin: " + e.getMessage());
        }
    }

    @Override
    public InterventionMedecin findById(Long id) throws InterventionMedecinException {
        try {
            return interventionRepo.findById(id);
        } catch (Exception e) {
            throw new InterventionMedecinException("Error finding InterventionMedecin: " + e.getMessage());
        }
    }

    @Override
    public List<InterventionMedecin> findAll() throws InterventionMedecinException {
        try {
            return interventionRepo.findAll();
        } catch (Exception e) {
            throw new InterventionMedecinException("Error fetching all InterventionMedecins: " + e.getMessage());
        }
    }

    @Override
    public void updateIntervention(InterventionMedecin intervention) throws InterventionMedecinException {
        try {
            interventionRepo.update(intervention);
        } catch (Exception e) {
            throw new InterventionMedecinException("Error updating InterventionMedecin: " + e.getMessage());
        }
    }

    @Override
    public void deleteInterventionById(Long id) throws InterventionMedecinException {
        try {
            interventionRepo.deleteById(id);
        } catch (Exception e) {
            throw new InterventionMedecinException("Error deleting InterventionMedecin: " + e.getMessage());
        }
    }

    @Override
    public List<InterventionMedecin> findByConsultation(Long consultationId) throws InterventionMedecinException {
        try {
            return interventionRepo.findByConsultation(consultationId);
        } catch (Exception e) {
            throw new InterventionMedecinException("Error fetching InterventionMedecins by Consultation: " + e.getMessage());
        }
    }
}
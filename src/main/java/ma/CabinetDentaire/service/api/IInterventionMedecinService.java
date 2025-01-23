package ma.CabinetDentaire.service.api;

import ma.CabinetDentaire.entities.InterventionMedecin;
import ma.CabinetDentaire.service.exceptions.InterventionMedecinException;

import java.util.List;

public interface IInterventionMedecinService {
    InterventionMedecin createIntervention(InterventionMedecin intervention) throws InterventionMedecinException;
    InterventionMedecin findById(Long id) throws InterventionMedecinException;
    List<InterventionMedecin> findAll() throws InterventionMedecinException;
    void updateIntervention(InterventionMedecin intervention) throws InterventionMedecinException;
    void deleteInterventionById(Long id) throws InterventionMedecinException;
    List<InterventionMedecin> findByConsultation(Long consultationId) throws InterventionMedecinException;
}
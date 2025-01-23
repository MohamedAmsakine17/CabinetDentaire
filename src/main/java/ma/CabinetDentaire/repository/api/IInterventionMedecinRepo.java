package ma.CabinetDentaire.repository.api;

import ma.CabinetDentaire.entities.InterventionMedecin;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.util.List;

public interface IInterventionMedecinRepo extends CRUDRepository<InterventionMedecin, Long> {
    List<InterventionMedecin> findByConsultation(Long consultationId) throws DaoException;
}
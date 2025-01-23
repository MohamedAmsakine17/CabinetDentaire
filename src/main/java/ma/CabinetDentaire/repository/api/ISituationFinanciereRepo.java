package ma.CabinetDentaire.repository.api;

import ma.CabinetDentaire.entities.SituationFinanciere;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.util.List;

public interface ISituationFinanciereRepo extends CRUDRepository<SituationFinanciere, Long> {
    SituationFinanciere findByDossierMedicale(Long dossierId) throws DaoException;
}
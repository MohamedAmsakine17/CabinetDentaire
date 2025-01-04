package ma.CabinetDentaire.repository.api;

import ma.CabinetDentaire.entities.DossierPatient;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

public interface IDossierRepo extends CRUDRepository<Patient,Long>  {

    DossierPatient findPatientById(Long id) throws DaoException;
}

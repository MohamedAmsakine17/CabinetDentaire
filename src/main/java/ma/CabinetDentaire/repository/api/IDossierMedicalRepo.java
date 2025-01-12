package ma.CabinetDentaire.repository.api;

import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

public interface IDossierMedicalRepo extends CRUDRepository<DossierMedicale,Long> {
    DossierMedicale findByPatientId(Long id) throws DaoException;
}

package ma.CabinetDentaire.service.api;

import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.service.exceptions.DossierMedicalExcption;

public interface IDossierMedicalService {
    DossierMedicale getDossierMedicaleByPateintId(Long pateintId) throws DossierMedicalExcption;
}

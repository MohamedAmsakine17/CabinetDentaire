package ma.CabinetDentaire.service;

import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.repository.api.IDossierMedicalRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.api.IDossierMedicalService;
import ma.CabinetDentaire.service.exceptions.DossierMedicalExcption;

public class DossierMedicalService implements IDossierMedicalService {
    IDossierMedicalRepo dossierMedicalRepo;

    public DossierMedicalService(IDossierMedicalRepo dossierMedicalRepo) {
        this.dossierMedicalRepo = dossierMedicalRepo;
    }

    @Override
    public DossierMedicale getDossierMedicaleByPateintId(Long pateintId) throws DossierMedicalExcption {
        try {
            return dossierMedicalRepo.findByPatientId(pateintId);
        } catch (DaoException e){
            throw new DossierMedicalExcption(e.getMessage());
        }
    }
}

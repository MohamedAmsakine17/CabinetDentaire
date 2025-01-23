package ma.CabinetDentaire.service;

import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.repository.api.IDossierMedicalRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.api.IConsultationService;
import ma.CabinetDentaire.service.api.IDossierMedicalService;
import ma.CabinetDentaire.service.api.IRendezVousService;
import ma.CabinetDentaire.service.exceptions.DossierMedicalExcption;

import java.util.List;

public class DossierMedicalService implements IDossierMedicalService {
    IDossierMedicalRepo dossierMedicalRepo;
    IRendezVousService rendezVousService;
    IConsultationService consultationService;

    public DossierMedicalService(IDossierMedicalRepo dossierMedicalRepo,IRendezVousService rendezVousService,IConsultationService consultationService) {
        this.dossierMedicalRepo = dossierMedicalRepo;
        this.rendezVousService = rendezVousService;
        this.consultationService = consultationService;
    }

    @Override
    public DossierMedicale getDossierMedicaleByPateintId(Long pateintId) throws DossierMedicalExcption {
        try {
            DossierMedicale dossierMedicale = dossierMedicalRepo.findByPatientId(pateintId);
            List<RendezVous> rendezVousList = rendezVousService.filterByDossier(dossierMedicale.getId());
            List<Consultation> consultations = consultationService.findByDossier(dossierMedicale.getId());

            dossierMedicale.setRdvs(rendezVousList);
            dossierMedicale.setConsultations(consultations);

            return dossierMedicale;
        } catch (DaoException e){
            throw new DossierMedicalExcption(e.getMessage());
        }
    }
}

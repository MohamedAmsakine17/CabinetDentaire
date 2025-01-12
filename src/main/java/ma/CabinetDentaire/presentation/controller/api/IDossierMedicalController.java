package ma.CabinetDentaire.presentation.controller.api;

import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.presentation.view.DossierMedicalView;

public interface IDossierMedicalController {
    DossierMedicalView showPatientDossierMedicale(Long id);
}

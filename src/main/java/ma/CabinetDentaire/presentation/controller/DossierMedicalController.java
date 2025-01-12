package ma.CabinetDentaire.presentation.controller;

import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.presentation.controller.api.IDossierMedicalController;
import ma.CabinetDentaire.presentation.view.DossierMedicalView;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.service.api.IDossierMedicalService;

public class DossierMedicalController implements IDossierMedicalController {
    Theme currentTheme;
    IDossierMedicalService dossierMedicalService;
    private DossierMedicalView view;

    public DossierMedicalController(Theme currentTheme, IDossierMedicalService service) {
        this.currentTheme = currentTheme;
        this.dossierMedicalService = service;
    }

    @Override
    public DossierMedicalView showPatientDossierMedicale(Long id) {
        view = new DossierMedicalView(currentTheme , dossierMedicalService.getDossierMedicaleByPateintId(id));
        return view;
    }
}

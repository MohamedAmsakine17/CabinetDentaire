package ma.CabinetDentaire.presentation.controller;

import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.Ordonnance;
import ma.CabinetDentaire.presentation.controller.api.IOrdonnanceController;
import ma.CabinetDentaire.presentation.view.DossierMedicalView;
import ma.CabinetDentaire.presentation.view.dossier_medical.OrdonnancePanel;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.service.api.IOrdonnanceService;
import ma.CabinetDentaire.service.exceptions.OrdonnanceException;

import java.time.LocalDate;
import java.util.List;

public class OrdonnanceController implements IOrdonnanceController {
    private final IOrdonnanceService ordonnanceService;
    private final Theme currentTheme;

    public OrdonnanceController(Theme currentTheme,IOrdonnanceService ordonnanceService) {
        this.ordonnanceService = ordonnanceService;
        this.currentTheme = currentTheme;
    }

    @Override
    public void createOrdonnancePanel(Consultation consultation) {
        DossierMedicalView.updateBodyContentPanel(new OrdonnancePanel(currentTheme).createOrdonnance(consultation));
    }

    @Override
    public void modifierOrdonnancePanel(Ordonnance ordonnance) {
        DossierMedicalView.updateBodyContentPanel(new OrdonnancePanel(currentTheme).modifierOrdonnance(ordonnance));
    }

    @Override
    public void displayOrddonnaceById(Long id) {
        DossierMedicalView.updateBodyContentPanel(new OrdonnancePanel(currentTheme).ordonnacePanel(ordonnanceService.findById(id)));
    }

    @Override
    public void createOrdonnance(LocalDate date, Consultation consultation) throws OrdonnanceException {
        Ordonnance ordonnance = ordonnanceService.createOrdonnance(new Ordonnance(0L,date,consultation));
        DossierMedicalView.updateBodyContentPanel(new OrdonnancePanel(currentTheme).ordonnacePanel(ordonnance));
    }

    @Override
    public Ordonnance findById(Long id) throws OrdonnanceException {
        return ordonnanceService.findById(id);
    }

    @Override
    public void findAll() throws OrdonnanceException {
        DossierMedicalView.updateBodyContentPanel(new OrdonnancePanel(currentTheme).ordonnanceAllPanels(ordonnanceService.findAll()));
    }

    @Override
    public void findAllByDossierId(Long dossierId) throws OrdonnanceException {
        DossierMedicalView.updateBodyContentPanel(new OrdonnancePanel(currentTheme).ordonnanceAllPanels(ordonnanceService.findAllByDossier(dossierId)));
    }

    @Override
    public Ordonnance findByConsultation(Long consultationId) throws OrdonnanceException {
        return ordonnanceService.findByConsultation(consultationId);
    }

    @Override
    public void updateOrdonnance(Ordonnance ordonnance) throws OrdonnanceException {
        ordonnanceService.updateOrdonnance(ordonnance);
        DossierMedicalView.updateBodyContentPanel(new OrdonnancePanel(currentTheme).ordonnacePanel(ordonnance));
    }

    @Override
    public void deleteOrdonnanceById(Long id,Long dossierId) throws OrdonnanceException {
        ordonnanceService.deleteOrdonnanceById(id);
        findAllByDossierId(dossierId);
    }
}
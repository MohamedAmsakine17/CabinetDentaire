package ma.CabinetDentaire.presentation.controller;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.SituationFinanciere;
import ma.CabinetDentaire.presentation.controller.api.ISituationFinanciereController;
import ma.CabinetDentaire.presentation.view.DossierMedicalView;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.api.ISituationFinanciereService;
import ma.CabinetDentaire.service.exceptions.SituationFinanciereException;

import java.time.LocalDate;
import java.util.List;

public class SituationFinanciereController implements ISituationFinanciereController {
    private final ISituationFinanciereService situationFinanciereService;
    private final Theme currentTheme;

    public SituationFinanciereController(Theme currentTheme, ISituationFinanciereService situationFinanciereService) {
        this.situationFinanciereService = situationFinanciereService;
        this.currentTheme = currentTheme;
    }

    @Override
    public void createSituationFinancierePanel() {
        //DossierMedicalView.updateBodyContentPanel(new SituationFinancierePanel(currentTheme).createSituationFinanciere());
    }

    @Override
    public void displaySituationFinanciereById(Long id) throws SituationFinanciereException {
        //DossierMedicalView.updateBodyContentPanel(new SituationFinancierePanel(currentTheme).situationFinancierePanel(situationFinanciereService.findById(id)));
    }

    @Override
    public void findAll() throws SituationFinanciereException {
        //DossierMedicalView.updateBodyContentPanel(new SituationFinancierePanel(currentTheme).situationFinanciereAllPanels(situationFinanciereService.findAll()));
    }

    @Override
    public void createSituationFinanciere(Double montantGlobalePaye, Double montantGlobaleRestant, LocalDate dateCreation, Long dossierId) throws SituationFinanciereException, DaoException {
        SituationFinanciere situationFinanciere = situationFinanciereService.createSituationFinanciere(new SituationFinanciere(0L, montantGlobalePaye, montantGlobaleRestant, dateCreation, AppFactory.getDossierMedicalRepo().findById(dossierId)));
        //DossierMedicalView.updateBodyContentPanel(new SituationFinancierePanel(currentTheme).situationFinancierePanel(situationFinanciere));
    }

    @Override
    public void updateSituationFinanciere(SituationFinanciere situationFinanciere) throws SituationFinanciereException {
        situationFinanciereService.updateSituationFinanciere(situationFinanciere);
        //DossierMedicalView.updateBodyContentPanel(new SituationFinancierePanel(currentTheme).situationFinancierePanel(situationFinanciere));
    }

    @Override
    public void deleteSituationFinanciereById(Long id) throws SituationFinanciereException {
        situationFinanciereService.deleteSituationFinanciereById(id);
        findAll();
    }

    @Override
    public void findByDossierMedicale(Long dossierId) throws SituationFinanciereException {
        //DossierMedicalView.updateBodyContentPanel(new SituationFinancierePanel(currentTheme).situationFinancierePanel(situationFinanciereService.findByDossierMedicale(dossierId)));
    }
}
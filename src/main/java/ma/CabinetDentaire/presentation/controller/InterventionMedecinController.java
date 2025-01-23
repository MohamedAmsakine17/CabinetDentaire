package ma.CabinetDentaire.presentation.controller;

import ma.CabinetDentaire.entities.Acte;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.InterventionMedecin;
import ma.CabinetDentaire.presentation.controller.api.IInterventionMedecinController;
import ma.CabinetDentaire.presentation.view.DossierMedicalView;
import ma.CabinetDentaire.presentation.view.dossier_medical.InterventionMedecinPanel;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.service.api.IInterventionMedecinService;
import ma.CabinetDentaire.service.exceptions.InterventionMedecinException;

import java.util.List;

public class InterventionMedecinController implements IInterventionMedecinController {
    private final IInterventionMedecinService interventionService;
    private final Theme currentTheme;

    public InterventionMedecinController(Theme currentTheme, IInterventionMedecinService interventionService) {
        this.interventionService = interventionService;
        this.currentTheme = currentTheme;
    }

    @Override
    public void createInterventionPanel(Consultation consultation) {
        DossierMedicalView.updateBodyContentPanel(new InterventionMedecinPanel(currentTheme).createIntervention(consultation));
    }

    @Override
    public void updateInterventionPanel(InterventionMedecin interventionMedecin) throws InterventionMedecinException {
        DossierMedicalView.updateBodyContentPanel(new InterventionMedecinPanel(currentTheme).updateIntervention(interventionMedecin));
    }

    @Override
    public void displayInterventionById(Long id) throws InterventionMedecinException {
        DossierMedicalView.updateBodyContentPanel(new InterventionMedecinPanel(currentTheme).interventionPanel(interventionService.findById(id)));
    }

    @Override
    public void findAll() throws InterventionMedecinException {
        DossierMedicalView.updateBodyContentPanel(new InterventionMedecinPanel(currentTheme).interventionAllPanels(interventionService.findAll()));
    }

    @Override
    public void createIntervention(Long dent, Double prixPatient, String noteMedecin, Consultation consultation, Acte acte) throws InterventionMedecinException {
        InterventionMedecin intervention = interventionService.createIntervention(new InterventionMedecin(0L, dent, prixPatient, noteMedecin, consultation, acte));
        findByConsultation(consultation.getIdConsultation());
    }

    @Override
    public void updateIntervention(InterventionMedecin intervention) throws InterventionMedecinException {
        interventionService.updateIntervention(intervention);
        findByConsultation(intervention.getConsultation().getIdConsultation());
    }

    @Override
    public void deleteInterventionById(Long id) throws InterventionMedecinException {
        interventionService.deleteInterventionById(id);
        findAll();
    }

    @Override
    public void findByConsultation(Long consultationId) throws InterventionMedecinException {
        DossierMedicalView.updateBodyContentPanel(new InterventionMedecinPanel(currentTheme).interventionAllPanels(interventionService.findByConsultation(consultationId)));
    }

    public List<InterventionMedecin> getAllByConsultation(Long consultationId) throws InterventionMedecinException {
        return interventionService.findByConsultation(consultationId);
    }
}
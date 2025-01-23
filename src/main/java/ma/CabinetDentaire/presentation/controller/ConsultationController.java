package ma.CabinetDentaire.presentation.controller;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.entities.enums.TypeConsultation;
import ma.CabinetDentaire.presentation.controller.api.IConsultationController;
import ma.CabinetDentaire.presentation.view.DossierMedicalView;
import ma.CabinetDentaire.presentation.view.MainView;
import ma.CabinetDentaire.presentation.view.dossier_medical.OrdonnancePanel;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.api.IConsultationService;
import ma.CabinetDentaire.service.exceptions.ConsultationException;
import ma.CabinetDentaire.service.exceptions.RendezVousException;

import java.time.LocalDate;
import java.util.List;

public class ConsultationController implements IConsultationController {
    private final IConsultationService consultationService;
    private final Theme currentTheme;

    public ConsultationController(Theme currentTheme, IConsultationService consultationService) {
        this.currentTheme = currentTheme;
        this.consultationService = consultationService;
    }


    @Override
    public Consultation createConsultation(TypeConsultation type, LocalDate date, DossierMedicale dossierMedicale) throws ConsultationException {
        Consultation consultation = new Consultation(0L,type,date,null,dossierMedicale);
        return consultationService.createConsultation(consultation);
    }
    public Consultation findConsultationById(Long id) throws ConsultationException {
        return consultationService.findById(id);
    }

    public List<Consultation> getAllConsultations() throws ConsultationException {
        return consultationService.findAll();
    }

    @Override
    public List<Consultation> filterByDossier(Long dossierId) {
        try {
            return consultationService.findByDossier(dossierId);
        } catch (RendezVousException e) {
            throw new RendezVousException("Error filtering RendezVous by dossier: " + e.getMessage());
        }
    }

    @Override
    public List<Consultation> getAllConsultationsByDossier(Long id) {
        return consultationService.findByDossier(id);
    }

    public void updateConsultation(Long idConsultation, TypeConsultation type, LocalDate date, DossierMedicale dossierMedicale) throws ConsultationException {
        Consultation consultation = new Consultation(idConsultation,type,date,null,dossierMedicale);
        consultationService.updateConsultation(consultation);
        MainView.updateRightPanel(AppFactory.getDossierMedicalController().showPatientDossierMedicale(dossierMedicale.getId()));
    }

    @Override
    public void updateConsultation(Consultation consultation) {
        consultationService.updateConsultation(consultation);
    }


    public void deleteConsultationById(Long id) throws ConsultationException {
        //Long dossierId= consultationService.findById(id).getDossierMedicale().getId();
        consultationService.deleteConsultationById(id);
       //MainView.updateRightPanel(AppFactory.getDossierMedicalController().showPatientDossierMedicale(dossierId));
    }
}
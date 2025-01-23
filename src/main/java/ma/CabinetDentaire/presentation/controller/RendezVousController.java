package ma.CabinetDentaire.presentation.controller;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.entities.enums.TypeRDV;
import ma.CabinetDentaire.presentation.controller.api.IRendezVousController;
import ma.CabinetDentaire.presentation.view.MainView;
import ma.CabinetDentaire.presentation.view.RendezVousView;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.api.IRendezVousService;
import ma.CabinetDentaire.service.exceptions.RendezVousException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RendezVousController implements IRendezVousController {

    private Theme currentTheme;
    private IRendezVousService rendezVousService;
    private RendezVousView rendezVousView;

    public RendezVousController(Theme currentTheme, IRendezVousService rendezVousService) {
        this.currentTheme = currentTheme;
        this.rendezVousService = rendezVousService;
    }

    public void setService(IRendezVousService service) {
        this.rendezVousService = service;
    }

    @Override
    public RendezVousView showAllRendezVous() {
        try {
            List<RendezVous> rendezVousList = rendezVousService.getAll();
            //rendezVousView = new RendezVousView(currentTheme, rendezVousList);
            return rendezVousView;
        } catch (RendezVousException e) {
            throw new RendezVousException("Error fetching all RendezVous: " + e.getMessage());
        }
    }

    @Override
    public List<RendezVous> filterByTypeRDV(TypeRDV typeRDV) {
        try {
            return rendezVousService.filterByTypeRDV(typeRDV);
        } catch (RendezVousException e) {
            throw new RendezVousException("Error filtering RendezVous by type: " + e.getMessage());
        }
    }

    @Override
    public List<RendezVous> filterByDate(LocalDate date) {
        try {
            return rendezVousService.filterByDate(date);
        } catch (RendezVousException e) {
            throw new RendezVousException("Error filtering RendezVous by date: " + e.getMessage());
        }
    }

    @Override
    public List<RendezVous> filterByDossier(Long dossierId) {
        try {
            return rendezVousService.filterByDossier(dossierId);
        } catch (RendezVousException e) {
            throw new RendezVousException("Error filtering RendezVous by dossier: " + e.getMessage());
        }
    }

    @Override
    public void createRendezVous(String motif, String temps, Long dossierId, String typeRDV, String dateRDV) {
        try {
            LocalTime tempsTime = LocalTime.parse(temps);
            LocalDate date = LocalDate.parse(dateRDV);
            TypeRDV typeRDVEnum = TypeRDV.valueOf(typeRDV);

            RendezVous rendezVous = new RendezVous(null, motif, tempsTime, AppFactory.getDossierMedicalRepo().findById(dossierId), null, typeRDVEnum, date);
            rendezVousService.createRendezVous(rendezVous);
            // Update the view
            MainView.updateRightPanel(AppFactory.getDossierMedicalController().showPatientDossierMedicale(dossierId));
        } catch (Exception e) {
            throw new RendezVousException("Error creating RendezVous: " + e.getMessage());
        }
    }

    @Override
    public void updateRendezVous(Long id, String motif, String temps, Long dossierId, String typeRDV, String dateRDV) {
        try {
            // Fetch the existing RendezVous
            RendezVous rendezVous = rendezVousService.filterById(id);
            rendezVous.setMotif(motif);
            rendezVous.setTemps(LocalTime.parse(temps));
            rendezVous.setDossier(AppFactory.getDossierMedicalRepo().findById(dossierId));
            rendezVous.setTypeRDV(TypeRDV.valueOf(typeRDV));
            rendezVous.setDateRDV(LocalDate.parse(dateRDV));

            // Update the RendezVous
            rendezVousService.updateRendezVous(rendezVous);

            // Update the view
            MainView.updateRightPanel(AppFactory.getDossierMedicalController().showPatientDossierMedicale(dossierId));
        } catch (Exception e) {
            throw new RendezVousException("Error updating RendezVous: " + e.getMessage());
        }
    }

    @Override
    public void updateRendezVous(RendezVous rendezVous) {
        rendezVousService.updateRendezVous(rendezVous);
        MainView.updateRightPanel(AppFactory.getDossierMedicalController().showPatientDossierMedicale(rendezVous.getDossier().getId()));
    }

    @Override
    public void deleteRendezVousById(Long id, Long dossierId) {
        try {
            rendezVousService.deleteRendezVousById(id);
            MainView.updateRightPanel(AppFactory.getDossierMedicalController().showPatientDossierMedicale(dossierId));
        } catch (RendezVousException e) {
            throw new RendezVousException("Error deleting RendezVous: " + e.getMessage());
        }
    }


}
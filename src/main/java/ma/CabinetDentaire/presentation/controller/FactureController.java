package ma.CabinetDentaire.presentation.controller;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Caisse;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.Facture;
import ma.CabinetDentaire.entities.enums.TypePaiement;
import ma.CabinetDentaire.presentation.controller.api.IFactureController;
import ma.CabinetDentaire.presentation.view.DossierMedicalView;
import ma.CabinetDentaire.presentation.view.dossier_medical.FacturePanel;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.api.IFactureService;
import ma.CabinetDentaire.service.exceptions.FactureException;

import java.time.LocalDate;
import java.util.List;

public class FactureController implements IFactureController {
    private final IFactureService factureService;
    private final Theme currentTheme;

    public FactureController(Theme currentTheme, IFactureService factureService) {
        this.factureService = factureService;
        this.currentTheme = currentTheme;
    }

    @Override
    public void createFacturePanel(Consultation consultation) throws DaoException {
        DossierMedicalView.updateBodyContentPanel(new FacturePanel(currentTheme).createFacture(consultation));
    }

    @Override
    public void displayFactureById(Long id) throws FactureException {
        //DossierMedicalView.updateBodyContentPanel(new FacturePanel(currentTheme).facturePanel(factureService.findById(id)));
    }

    @Override
    public void findAll() throws FactureException {
        //DossierMedicalView.updateBodyContentPanel(new FacturePanel(currentTheme).factureAllPanels(factureService.findAll()));
    }

    @Override
    public void createFacture(Double montantTotal, Double montantPaye, LocalDate dateFacturation, Long situationFinanciereId, TypePaiement typePaiement, Consultation consultation) throws FactureException, DaoException {
        Facture facture = factureService.createFacture(new Facture(
                0L, montantTotal, montantTotal - montantPaye, montantPaye, dateFacturation, AppFactory.getSituationRepo().findById(situationFinanciereId), typePaiement, consultation
        ));

        Caisse caisse = Caisse.getInstance();
        List<Facture> factures = factureService.findAll();
        caisse.updateRecettes(factures);

        findByConsultation(consultation.getIdConsultation());
    }

    @Override
    public void updateFacture(Facture facture) throws FactureException {
        factureService.updateFacture(facture);
        findByConsultation(facture.getConsultation().getIdConsultation());
    }

    @Override
    public void deleteFactureById(Long id) throws FactureException {
        factureService.deleteFactureById(id);
        findAll();
    }

    @Override
    public void findByConsultation(Long consultationId) throws FactureException {
        DossierMedicalView.updateBodyContentPanel(new FacturePanel(currentTheme).allFacturesPanel(factureService.findByConsultation(consultationId)));
    }
}
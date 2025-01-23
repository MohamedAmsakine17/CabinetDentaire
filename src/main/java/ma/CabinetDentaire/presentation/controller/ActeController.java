package ma.CabinetDentaire.presentation.controller;

import ma.CabinetDentaire.entities.Acte;
import ma.CabinetDentaire.entities.enums.CategorieActe;
import ma.CabinetDentaire.presentation.controller.api.IActeController;
import ma.CabinetDentaire.presentation.view.DossierMedicalView;
import ma.CabinetDentaire.presentation.view.dossier_medical.ActePanel;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.service.api.IActeService;
import ma.CabinetDentaire.service.exceptions.ActeException;

import java.util.List;

public class ActeController implements IActeController {
    private final IActeService acteService;
    private final Theme currentTheme;

    public ActeController(Theme currentTheme, IActeService acteService) {
        this.acteService = acteService;
        this.currentTheme = currentTheme;
    }

    public void updateActePanel(Acte acte){
        DossierMedicalView.updateBodyContentPanel(new ActePanel(currentTheme).updateActe(acte));
    }

    @Override
    public void createActePanel() {
        DossierMedicalView.updateBodyContentPanel(new ActePanel(currentTheme).createActe());
    }

    @Override
    public void displayActeById(Long id) throws ActeException {
        DossierMedicalView.updateBodyContentPanel(new ActePanel(currentTheme).actePanel(acteService.findById(id)));
    }

    @Override
    public void findAll() throws ActeException {
        DossierMedicalView.updateBodyContentPanel(new ActePanel(currentTheme).acteAllPanels(acteService.findAll()));
    }

    public List<Acte> findAllActe() throws ActeException {
        return acteService.findAll();
    }

    @Override
    public void createActe(String libelle, Double prixDeBase, CategorieActe categorie) throws ActeException {
        Acte acte = acteService.createActe(new Acte(0L, libelle, prixDeBase, categorie));
        findAll();
    }

    @Override
    public void updateActe(Acte acte) throws ActeException {
        acteService.updateActe(acte);
        findAll();
    }

    @Override
    public void deleteActeById(Long id) throws ActeException {
        acteService.deleteActeById(id);
        findAll();
    }
}
package ma.CabinetDentaire.presentation.controller;

import com.sun.tools.javac.Main;
import ma.CabinetDentaire.entities.Medicament;
import ma.CabinetDentaire.presentation.controller.api.IMedicamentController;
import ma.CabinetDentaire.presentation.view.MainView;
import ma.CabinetDentaire.presentation.view.Medicament.MedicamentView;
import ma.CabinetDentaire.presentation.view.Medicament.ModifierMedicamentView;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.service.api.IMedicamentService;

import java.util.List;

public class MedicamentController implements IMedicamentController {
    Theme currentTheme;
    IMedicamentService medicamentService;
    MedicamentView medicamentView;
    ModifierMedicamentView modifierMedicamentView;

    public MedicamentController(Theme currentTheme, IMedicamentService medicamentService) {
        this.currentTheme = currentTheme;
        this.medicamentService = medicamentService;
    }

    @Override
    public MedicamentView showAllMedicaments() {
        medicamentView = new MedicamentView(currentTheme, medicamentService.getAll());
        return medicamentView;
    }

    @Override
    public ModifierMedicamentView showModifierMedicamentView(Medicament medicament) {
        modifierMedicamentView = new ModifierMedicamentView(currentTheme,medicament);
        return modifierMedicamentView;
    }

    @Override
    public List<Medicament> getAllMedicament() {
        return  medicamentService.getAll();
    }

    @Override
    public Medicament getMedicamentByName(String name) {
        return medicamentService.getMedicamentByName(name);
    }

    @Override
    public void createMedicament(double prix, String nom, String description, String imageSource) {
        //ID|PRIX|NOM|DESCRIPTION|image
        medicamentService.addMedicament(new Medicament(0L, prix, nom, description, imageSource));
        MainView.updateRightPanel(showAllMedicaments());
    }

    @Override
    public void updateMedicament(Long id, double prix, String nom, String description, String imageSource) {
        medicamentService.updateMedicament(new Medicament(id, prix, nom, description, imageSource));
        MainView.updateRightPanel(showAllMedicaments());
    }

    @Override
    public void deleteMedicament(Long id) {
        medicamentService.deleteMedicamentById(id);
        MainView.updateRightPanel(showAllMedicaments());
    }
}

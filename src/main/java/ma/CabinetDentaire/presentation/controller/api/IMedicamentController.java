package ma.CabinetDentaire.presentation.controller.api;

import ma.CabinetDentaire.entities.Medicament;
import ma.CabinetDentaire.presentation.view.Medicament.MedicamentView;
import ma.CabinetDentaire.presentation.view.Medicament.ModifierMedicamentView;

public interface IMedicamentController {
    MedicamentView showAllMedicaments();
    ModifierMedicamentView showModifierMedicamentView(Medicament medicament);
    void createMedicament(double prix, String nom, String description, String imageSource);
    void updateMedicament(Long id,double prix, String nom, String description, String imageSource);
    void deleteMedicament(Long id);
}

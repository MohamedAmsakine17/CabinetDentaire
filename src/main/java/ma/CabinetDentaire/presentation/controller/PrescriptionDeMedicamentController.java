package ma.CabinetDentaire.presentation.controller;

import ma.CabinetDentaire.entities.Ordonnance;
import ma.CabinetDentaire.entities.PrescriptionDeMedicament;
import ma.CabinetDentaire.presentation.controller.api.IPrescriptionDeMedicamentController;
import ma.CabinetDentaire.presentation.view.DossierMedicalView;
import ma.CabinetDentaire.presentation.view.dossier_medical.OrdonnancePanel;
import ma.CabinetDentaire.presentation.view.dossier_medical.PrescriptionDeMedicamentPanel;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.service.api.IPrescriptionDeMedicamentService;
import ma.CabinetDentaire.service.exceptions.PrescriptionDeMedicamentException;

import java.util.List;

public class PrescriptionDeMedicamentController implements IPrescriptionDeMedicamentController {
    private final IPrescriptionDeMedicamentService prescriptionService;
    private Theme currentTheme;

    public PrescriptionDeMedicamentController(Theme currentTheme,IPrescriptionDeMedicamentService prescriptionService) {
        this.prescriptionService = prescriptionService;
        this.currentTheme = currentTheme;
    }

    public void displayPrescriptionDeMedicament(PrescriptionDeMedicament prescriptionDeMedicament) {
        DossierMedicalView.updateBodyContentPanel(new PrescriptionDeMedicamentPanel(currentTheme).prescriptionDeMedicamentPanel(prescriptionDeMedicament));
    }

    public void createPrescription(Ordonnance ordonnance){
        DossierMedicalView.updateBodyContentPanel(new PrescriptionDeMedicamentPanel(currentTheme).createPrescriptionMedicamentPanel(ordonnance));
    }

    public void modifieirPrescription(PrescriptionDeMedicament prescriptionDeMedicament){
        DossierMedicalView.updateBodyContentPanel(new PrescriptionDeMedicamentPanel(currentTheme).modifierPrescriptionMedicamentPanel(prescriptionDeMedicament));
    }

    @Override
    public void createPrescription(int unitesMin, int unitesMax, String contraintesAlimentation, Long ordonnanceId, String contraintesTemps, Long medicamentId) throws PrescriptionDeMedicamentException {
        PrescriptionDeMedicament prescriptionDeMedicament =  prescriptionService.createPrescription(unitesMin, unitesMax, contraintesAlimentation, ordonnanceId, contraintesTemps, medicamentId);
        //DossierMedicalView.updateBodyContentPanel(new PrescriptionDeMedicamentPanel(currentTheme).createPrescriptionMedicamentPanel(ordonnance));
    }

    @Override
    public PrescriptionDeMedicament findById(Long id) throws PrescriptionDeMedicamentException {
        return prescriptionService.findById(id);
    }

    @Override
    public List<PrescriptionDeMedicament> findAll() throws PrescriptionDeMedicamentException {
        return prescriptionService.findAll();
    }

    @Override
    public List<PrescriptionDeMedicament> findByOrdonnance(Long ordonnanceId) throws PrescriptionDeMedicamentException {
        return prescriptionService.findByOrdonnance(ordonnanceId);
    }

    @Override
    public void updatePrescription(PrescriptionDeMedicament prescription) throws PrescriptionDeMedicamentException {
        prescriptionService.updatePrescription(prescription);
    }

    @Override
    public void deletePrescriptionById(Long id) throws PrescriptionDeMedicamentException {
        prescriptionService.deletePrescriptionById(id);
    }
}
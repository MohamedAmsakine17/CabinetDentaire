package ma.CabinetDentaire.config;

import ma.CabinetDentaire.presentation.controller.*;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.presentation.view.themes.ThemeLight;
import ma.CabinetDentaire.repository.fileDB_impl.*;
import ma.CabinetDentaire.service.*;

public class AppFactory {
    static Theme currentTheme;

    static PatientRepo patientRepo;
    static PatientService patientService;
    static PatientController patientController;

    static RendezVousRepo rendezVousRepo;
    static RendezVousService rendezVousService;
    static RendezVousController rendezVousController;

    static DossierMedicalRepo dossierMedicalRepo;
    static DossierMedicalService dossierMedicalService;
    static DossierMedicalController dossierMedicalController;

    static MedicamentRepo medicamentRepo;
    static MedicamentService medicamentService;
    static MedicamentController medicamentController;

    static ConsultationRepo consultationRepo;
    static ConsultationService consultationService;
    static ConsultationController consultationController;

    static OrdonnanceRepo ordonnanceRepo;
    static OrdonnanceService ordonnanceService;
    static OrdonnanceController ordonnanceController;

    static PrescriptionDeMedicamentRepo prescriptionRepo;
    static PrescriptionDeMedicamentService prescriptionService;
    static PrescriptionDeMedicamentController prescriptionController;

    static ActeRepo acteRepo;
    static ActeService acteService;
    static ActeController acteController;

    static InterventionMedecinRepo interventionRepo;
    static InterventionMedecinService interventionService;
    static InterventionMedecinController interventionController;

    static SituationFinanciereRepo situationRepo;
    static SituationFinanciereService situationService;
    static SituationFinanciereController situationController;

    static FactureRepo factureRepo;
    static FactureService factureService;
    static FactureController factureController;

    static UtilisateurDAO utilisateurDAO;


    static {
        currentTheme = new ThemeLight();

        patientRepo = new PatientRepo();
        dossierMedicalRepo = new DossierMedicalRepo();
        medicamentRepo = new MedicamentRepo();
        rendezVousRepo = new RendezVousRepo();
        consultationRepo = new ConsultationRepo();
        ordonnanceRepo = new OrdonnanceRepo();
        prescriptionRepo = new PrescriptionDeMedicamentRepo();
        acteRepo = new ActeRepo();
        interventionRepo = new InterventionMedecinRepo();
        situationRepo = new SituationFinanciereRepo();
        factureRepo = new FactureRepo();

        dossierMedicalRepo.setPatientRepo(patientRepo);
        rendezVousRepo.setDossierMedicalRepo(dossierMedicalRepo);
        rendezVousRepo.setConsultationRepo(consultationRepo);
        consultationRepo.setDossierMedicalRepo(dossierMedicalRepo);
        prescriptionRepo.setOrdonnanceRepo(ordonnanceRepo);
        prescriptionRepo.setMedicamentRepo(medicamentRepo);

        patientService =  new PatientService(patientRepo,dossierMedicalRepo);
        patientController = new PatientController(currentTheme,patientService);

        rendezVousService = new RendezVousService(rendezVousRepo);
        rendezVousController = new RendezVousController(currentTheme,rendezVousService);

        consultationService =  new ConsultationService(consultationRepo);
        consultationController = new ConsultationController(currentTheme,consultationService);

        dossierMedicalService = new DossierMedicalService(dossierMedicalRepo,rendezVousService,consultationService);
        dossierMedicalController = new DossierMedicalController(currentTheme,dossierMedicalService);

        ordonnanceRepo.setConsultationRepo(consultationRepo);
        ordonnanceService = new OrdonnanceService(ordonnanceRepo,prescriptionRepo);
        ordonnanceController = new OrdonnanceController(currentTheme,ordonnanceService);

        prescriptionService = new PrescriptionDeMedicamentService(prescriptionRepo);
        prescriptionController = new PrescriptionDeMedicamentController(currentTheme,prescriptionService);

        medicamentService =  new MedicamentService(medicamentRepo);
        medicamentController = new MedicamentController(currentTheme,medicamentService);

        acteService =  new ActeService(acteRepo);
        acteController = new ActeController(currentTheme,acteService);

        interventionService =  new InterventionMedecinService(interventionRepo);
        interventionController = new InterventionMedecinController(currentTheme,interventionService);

        situationService =  new SituationFinanciereService(situationRepo);
        situationController = new SituationFinanciereController(currentTheme,situationService);

        factureService =  new FactureService(factureRepo);
        factureController = new FactureController(currentTheme,factureService);
        //patientRepo.setDossierMedicalRepo(dossierMedicalRepo);

        utilisateurDAO = new UtilisateurDAO();
    }

    public static FactureRepo getFactureRepo() {
        return factureRepo;
    }

    public static FactureService getFactureService() {
        return factureService;
    }

    public static FactureController getFactureController() {
        return factureController;
    }

    public static SituationFinanciereRepo getSituationRepo() {
        return situationRepo;
    }

    public static SituationFinanciereService getSituationService() {
        return situationService;
    }

    public static SituationFinanciereController getSituationController() {
        return situationController;
    }

    public static InterventionMedecinRepo getInterventionRepo() {
        return interventionRepo;
    }

    public static InterventionMedecinService getInterventionService() {
        return interventionService;
    }

    public static InterventionMedecinController getInterventionController() {
        return interventionController;
    }

    public static PatientRepo getPatientRepo() {
        return patientRepo;
    }

    public static PatientService getPatientService() {
        return patientService;
    }

    public static PatientController getPatientController() {
        return patientController;
    }

    public static UtilisateurDAO getUtilisateurDAO() {
        return utilisateurDAO;
    }

    public static DossierMedicalRepo getDossierMedicalRepo() {
        return dossierMedicalRepo;
    }

    public static void setDossierMedicalRepo(DossierMedicalRepo dossierMedicalRepo) {
        AppFactory.dossierMedicalRepo = dossierMedicalRepo;
    }

    public static DossierMedicalService getDossierMedicalService() {
        return dossierMedicalService;
    }

    public static void setDossierMedicalService(DossierMedicalService dossierMedicalService) {
        AppFactory.dossierMedicalService = dossierMedicalService;
    }

    public static DossierMedicalController getDossierMedicalController() {
        return dossierMedicalController;
    }

    public static void setDossierMedicalController(DossierMedicalController dossierMedicalController) {
        AppFactory.dossierMedicalController = dossierMedicalController;
    }

    public static MedicamentController getMedicamentController() {
        return medicamentController;
    }

    public static void setMedicamentController(MedicamentController medicamentController) {
        AppFactory.medicamentController = medicamentController;
    }

    public static RendezVousRepo getRendezVousRepo() {
        return rendezVousRepo;
    }

    public static RendezVousService getRendezVousService() {
        return rendezVousService;
    }

    public static RendezVousController getRendezVousController() {
        return rendezVousController;
    }


    public static ConsultationRepo getConsultationRepo() {
        return consultationRepo;
    }

    public static ConsultationService getConsultationService() {
        return consultationService;
    }

    public static ConsultationController getConsultationController() {
        return consultationController;
    }

    public static OrdonnanceRepo getOrdonnanceRepo() {
        return ordonnanceRepo;
    }

    public static OrdonnanceService getOrdonnanceService() {
        return ordonnanceService;
    }

    public static OrdonnanceController getOrdonnanceController() {
        return ordonnanceController;
    }

    public static Theme getCurrentTheme() {
        return currentTheme;
    }

    public static ActeRepo getActeRepo() {
        return acteRepo;
    }

    public static ActeService getActeService() {
        return acteService;
    }

    public static ActeController getActeController() {
        return acteController;
    }

    public static MedicamentRepo getMedicamentRepo() {
        return medicamentRepo;
    }

    public static MedicamentService getMedicamentService() {
        return medicamentService;
    }

    public static PrescriptionDeMedicamentRepo getPrescriptionRepo() {
        return prescriptionRepo;
    }

    public static PrescriptionDeMedicamentService getPrescriptionService() {
        return prescriptionService;
    }

    public static PrescriptionDeMedicamentController getPrescriptionController() {
        return prescriptionController;
    }
}

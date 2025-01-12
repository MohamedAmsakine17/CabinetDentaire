package ma.CabinetDentaire.config;

import ma.CabinetDentaire.presentation.controller.DossierMedicalController;
import ma.CabinetDentaire.presentation.controller.PatientController;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.presentation.view.themes.ThemeLight;
import ma.CabinetDentaire.repository.fileDB_impl.DossierMedicalRepo;
import ma.CabinetDentaire.repository.fileDB_impl.PatientRepo;
import ma.CabinetDentaire.repository.fileDB_impl.UtilisateurDAO;
import ma.CabinetDentaire.service.DossierMedicalService;
import ma.CabinetDentaire.service.PatientService;

public class AppFactory {
    static Theme currentTheme;

    static PatientRepo patientRepo;
    static PatientService patientService;
    static PatientController patientController;

    static DossierMedicalRepo dossierMedicalRepo;
    static DossierMedicalService dossierMedicalService;
    static DossierMedicalController dossierMedicalController;

    static UtilisateurDAO utilisateurDAO;

    static {
        currentTheme = new ThemeLight();

        patientRepo = new PatientRepo();
        dossierMedicalRepo = new DossierMedicalRepo();

        patientService =  new PatientService(patientRepo,dossierMedicalRepo);
        patientController = new PatientController(currentTheme,patientService);

        dossierMedicalRepo.setPatientRepo(patientRepo);
        dossierMedicalService = new DossierMedicalService(dossierMedicalRepo);
        dossierMedicalController = new DossierMedicalController(currentTheme,dossierMedicalService);
        //dossierMedicalRepo = new DossierMedicalRepo();
        //dossierMedicalRepo.setPatientRepo(patientRepo);

        //patientRepo.setDossierMedicalRepo(dossierMedicalRepo);

        utilisateurDAO = new UtilisateurDAO();
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
}

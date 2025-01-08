package ma.CabinetDentaire.config;

import ma.CabinetDentaire.presentation.controller.PatientController;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.presentation.view.themes.ThemeLight;
import ma.CabinetDentaire.repository.fileDB_impl.PatientRepo;
import ma.CabinetDentaire.repository.fileDB_impl.UtilisateurDAO;
import ma.CabinetDentaire.service.PatientService;

public class AppFactory {
    static Theme currentTheme;

    static PatientRepo patientRepo;
    static PatientService patientService;
    static PatientController patientController;


    static UtilisateurDAO utilisateurDAO;

    static {
        currentTheme = new ThemeLight();

        patientRepo = new PatientRepo();
        patientService =  new PatientService(patientRepo);
        patientController = new PatientController(currentTheme,patientService);

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
}

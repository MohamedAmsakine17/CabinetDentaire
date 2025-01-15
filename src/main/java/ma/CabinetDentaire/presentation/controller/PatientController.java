package ma.CabinetDentaire.presentation.controller;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.GroupeSanguin;
import ma.CabinetDentaire.entities.enums.Mutuelle;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.presentation.controller.api.IPatientController;
import ma.CabinetDentaire.presentation.view.MainView;
import ma.CabinetDentaire.presentation.view.patient.ModifierPatientView;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.presentation.view.patient.PatientView;
import ma.CabinetDentaire.service.api.IPatientService;
import ma.CabinetDentaire.service.exceptions.PatientException;

import java.time.LocalDate;
import java.util.List;

public class PatientController implements IPatientController {

    private Theme currentTheme;
    IPatientService patientService;
    private PatientView patientView;
    private ModifierPatientView modifierPatientView;

    public PatientController(Theme currentTheme, IPatientService patientService) {
        this.currentTheme = currentTheme;
        this.patientService = patientService;
    }

    public void setService(IPatientService service) {
        this.patientService = service;
    }

    @Override
    public PatientView showAllPatients() {
        try{
            patientView = new PatientView(currentTheme, patientService.getAll(), patientService.fillterBySexe(Sexe.HOMME).size(), patientService.filterByAge(14).size());
            return patientView;
        } catch (PatientException e){
            throw new PatientException(e.getMessage());
        }
    }

    @Override
    public ModifierPatientView showPatientsModifier(Patient patient) {
        modifierPatientView = new ModifierPatientView(currentTheme, patient);
        return modifierPatientView;
    }

    @Override
    public List<Patient> filterByName(String keyword) {
        try {
            return patientService.filterByName(keyword);
        } catch (PatientException e) {
            throw new RuntimeException("Error filtering patients by name: " + e.getMessage());
        }
    }

    @Override
    public void createPatient() {
        //ID|NOM|PRENOM|CIN|ADRESSE|TELEPHONE|EMAIL|PFP|DATA_NAISSANCE|SEXE|GROUP_SANGUIN|MUTUELLE|PROFESSION|DOSSIERMEDICALE
        String nom = patientView.getAjouterPatientPanel().getNom();
        String prenom = patientView.getAjouterPatientPanel().getPrenom();
        String cin = patientView.getAjouterPatientPanel().getCin();
        String address = patientView.getAjouterPatientPanel().getAddress();
        String telephone = patientView.getAjouterPatientPanel().getTelephone();
        String email = patientView.getAjouterPatientPanel().getEmail();
        String pfp = patientView.getAjouterPatientPanel().getPfpPath();
        LocalDate dateNaissance = patientView.getAjouterPatientPanel().getDateNaissance();
        Sexe sexe = patientView.getAjouterPatientPanel().getSexe();
        Mutuelle mutuelle = patientView.getAjouterPatientPanel().getAssurance();
        GroupeSanguin groupeSanguin = GroupeSanguin.O_POSITIF;

        Patient patient = patientService.createPatient(new Patient(0L,nom,prenom,cin,address,telephone,email,pfp,dateNaissance,sexe,groupeSanguin,mutuelle,null));

        MainView.updateRightPanel(AppFactory.getDossierMedicalController().showPatientDossierMedicale(patient.getId()));
    }

    @Override
    public void updatePatient(Long id, String nom, String prenom,String cin, String address, String telephone, String email, String pfp, LocalDate dateNaissance, Sexe sexe, Mutuelle mutuelle) {
        patientService.updatePatient(new Patient(id,nom,prenom,cin,address,telephone,email,pfp,dateNaissance,sexe,GroupeSanguin.O_POSITIF,mutuelle,null));
        MainView.updateRightPanel(AppFactory.getDossierMedicalController().showPatientDossierMedicale(id));
    }

    @Override
    public void deletePatientById(Long id) {
        patientService.deletePatientById(id);
        MainView.updateRightPanel(showAllPatients());
    }
}

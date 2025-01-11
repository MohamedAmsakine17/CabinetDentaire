package ma.CabinetDentaire.presentation.controller;

import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.GroupeSanguin;
import ma.CabinetDentaire.entities.enums.Mutuelle;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.presentation.controller.api.IPatientController;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.presentation.view.PatientView;
import ma.CabinetDentaire.service.IPatientService;
import ma.CabinetDentaire.service.exceptions.PatientException;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class PatientController implements IPatientController {

    private Theme currentTheme;
    IPatientService patientService;
    private PatientView patientView;

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

        Patient patient = new Patient(0L,nom,prenom,cin,address,telephone,email,pfp,dateNaissance,sexe,groupeSanguin,mutuelle,null);
        patientService.createPatient(patient);

        showAllPatients();
    }


}

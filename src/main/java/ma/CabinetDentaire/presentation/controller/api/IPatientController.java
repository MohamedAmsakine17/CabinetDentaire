package ma.CabinetDentaire.presentation.controller.api;

import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.Mutuelle;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.presentation.view.patient.ModifierPatientView;
import ma.CabinetDentaire.presentation.view.patient.PatientView;

import java.time.LocalDate;
import java.util.List;

public interface IPatientController {
    PatientView showAllPatients();
    ModifierPatientView showPatientsModifier(Patient patient);
    List<Patient> filterByName(String keyword);
    void createPatient();
    void updatePatient(Long id, String nom, String prenom ,String cin, String address, String telephone, String email, String pfp, LocalDate dateNaissance, Sexe sexe, Mutuelle mutuelle);
    void deletePatientById(Long id);
}

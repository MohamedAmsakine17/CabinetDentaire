package ma.CabinetDentaire.presentation.controller.api;

import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.presentation.view.PatientView;

import java.util.List;

public interface IPatientController {
    PatientView showAllPatients();
    List<Patient> filterByName(String keyword);
    void createPatient();
}

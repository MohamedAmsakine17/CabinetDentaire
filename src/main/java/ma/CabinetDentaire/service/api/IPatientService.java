package ma.CabinetDentaire.service.api;

import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.service.exceptions.PatientException;

import java.util.List;

public interface IPatientService {
    List<Patient> getAll() throws PatientException;
    List<Patient> fillterBySexe(Sexe sexe) throws PatientException;
    List<Patient> filterByAge(int age) throws PatientException;
    List<Patient> filterByName(String keyword) throws PatientException;
    Patient createPatient(Patient patient) throws PatientException;
}

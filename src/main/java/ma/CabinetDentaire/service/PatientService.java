package ma.CabinetDentaire.service;

import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.repository.api.IPatientRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.exceptions.PatientException;

import java.util.List;

public class PatientService implements IPatientService {
    private IPatientRepo patientRepo;

    public PatientService(IPatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    @Override
    public List<Patient> getAll() throws PatientException {
        try {
            return patientRepo.findAll();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Patient> fillterBySexe(Sexe sexe) throws PatientException {
        try {
            return patientRepo.findBySexe(sexe);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Patient> filterByAge(int age) throws PatientException {
        try {
            return patientRepo.findUnderAge(age);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Patient> filterByName(String keyword) throws PatientException {
        try{
            return patientRepo.findByNameLike(keyword);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Patient createPatient(Patient patient) throws PatientException {
        try{
            return patientRepo.save(patient);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }


}

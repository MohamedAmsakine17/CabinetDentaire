package ma.CabinetDentaire.service;

import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.entities.enums.StatutPaiment;
import ma.CabinetDentaire.repository.api.IDossierMedicalRepo;
import ma.CabinetDentaire.repository.api.IPatientRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.api.IPatientService;
import ma.CabinetDentaire.service.exceptions.PatientException;

import java.time.LocalDate;
import java.util.List;

public class PatientService implements IPatientService {
    private IPatientRepo patientRepo;
    private IDossierMedicalRepo dossierMedicalRepo;

    public PatientService(IPatientRepo patientRepo, IDossierMedicalRepo dossierMedicalRepo) {
        this.patientRepo = patientRepo;
        this.dossierMedicalRepo = dossierMedicalRepo;
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
            Patient newPatient = patientRepo.save(patient);
            dossierMedicalRepo.save(new DossierMedicale(0L,newPatient, LocalDate.now(), StatutPaiment.EN_ATTENTE));
            return newPatient;
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePatient(Patient patient) throws PatientException {
        try{
            patientRepo.update(patient);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePatientById(Long id) throws PatientException {
        try{
            dossierMedicalRepo.deleteById(dossierMedicalRepo.findByPatientId(id).getId());
            patientRepo.deleteById(id);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}

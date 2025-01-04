package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.DossierPatient;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.repository.api.IDossierRepo;
import ma.CabinetDentaire.repository.api.IPatientRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.File;
import java.util.List;

public class DossierRepo implements IDossierRepo {
    private static final File DOSSIER_PATIENT_TABLE = new File("src/main/data/patients.txt");
    private IPatientRepo patientRepo;

    @Override
    public DossierPatient findPatientById(Long id) throws DaoException {
        return null;
    }

    @Override
    public List<Patient> findAll() throws DaoException {
        return List.of();
    }

    @Override
    public Patient findById(Long aLong) throws DaoException {
        return null;
    }

    @Override
    public Patient save(Patient element) throws DaoException {
        return null;
    }

    @Override
    public void update(Patient element) throws DaoException {

    }

    @Override
    public void delete(Patient element) throws DaoException {

    }

    @Override
    public void deleteById(Long aLong) throws DaoException {

    }
}

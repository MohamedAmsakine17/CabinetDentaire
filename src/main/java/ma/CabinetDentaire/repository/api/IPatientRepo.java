package ma.CabinetDentaire.repository.api;

import java.util.ArrayList;
import java.util.List;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

public interface IPatientRepo  extends CRUDRepository<Patient,Long> {
    Patient findByCIN(String cin)           throws DaoException;
    List<Patient> findByCINLike(String keyword) throws DaoException;
    List<Patient> findBySexe(Sexe sexe)     throws DaoException;
    List<Patient> findUnderAge(int age) throws DaoException;
    List<Patient> findByNameLike(String keyword) throws DaoException;
}

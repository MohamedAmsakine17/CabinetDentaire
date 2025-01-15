package ma.CabinetDentaire.service;

import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.Medicament;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.StatutPaiment;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.repository.fileDB_impl.MedicamentRepo;
import ma.CabinetDentaire.service.api.IMedicamentService;
import ma.CabinetDentaire.service.exceptions.MedicamentException;

import java.time.LocalDate;
import java.util.List;

public class MedicamentService implements IMedicamentService {
    MedicamentRepo medicamentRepo;

    public MedicamentService(MedicamentRepo medicamentRepo) {
        this.medicamentRepo = medicamentRepo;
    }


    @Override
    public List<Medicament> getAll() throws MedicamentException {
        try {
            return medicamentRepo.findAll();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Medicament getMedicamentById(Long id) throws MedicamentException {
        try {
            return medicamentRepo.findById(id);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Medicament addMedicament(Medicament m) throws MedicamentException {
        try{
            return medicamentRepo.save(m);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateMedicament(Medicament m) throws MedicamentException {
        try{
            medicamentRepo.update(m);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMedicamentById(Long id) throws MedicamentException {
        try{
            medicamentRepo.deleteById(id);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}

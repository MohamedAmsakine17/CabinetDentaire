package ma.CabinetDentaire.service.api;

import ma.CabinetDentaire.entities.Medicament;
import ma.CabinetDentaire.service.exceptions.MedicamentException;

import java.util.List;

public interface IMedicamentService {
    List<Medicament> getAll() throws MedicamentException;
    Medicament getMedicamentById(Long id) throws MedicamentException;
    Medicament getMedicamentByName(String name) throws MedicamentException;
    Medicament addMedicament(Medicament m) throws MedicamentException;
    void updateMedicament(Medicament m) throws MedicamentException;
    void deleteMedicamentById(Long id) throws MedicamentException;
}

package ma.CabinetDentaire.repository.api;

import ma.CabinetDentaire.entities.Medicament;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

public interface IMedicamentRepo extends CRUDRepository<Medicament,Long> {
    Medicament getMedicamentByName(String name) throws DaoException;
}

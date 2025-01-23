package ma.CabinetDentaire.repository.api;

import ma.CabinetDentaire.entities.Acte;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

public interface IActeRepo  extends CRUDRepository<Acte,Long> {
    Acte findByLibelle(String libelle) throws DaoException;
}

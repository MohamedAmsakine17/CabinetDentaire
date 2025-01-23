package ma.CabinetDentaire.repository.api;

import ma.CabinetDentaire.entities.Facture;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.util.List;

public interface IFactureRepo extends CRUDRepository<Facture, Long> {
    List<Facture> findByConsultation(Long consultationId) throws DaoException;
    List<Facture> findBySituationFinancie(Long id) throws DaoException;
}
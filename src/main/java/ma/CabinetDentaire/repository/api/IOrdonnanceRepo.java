package ma.CabinetDentaire.repository.api;

import ma.CabinetDentaire.entities.Ordonnance;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.util.List;

public interface IOrdonnanceRepo extends CRUDRepository<Ordonnance, Long> {
    Ordonnance findByConsultation(Long consultationId) throws DaoException;
    List<Ordonnance> findAllByDossierId(Long dossierId) throws DaoException;
}
package ma.CabinetDentaire.repository.api;

import ma.CabinetDentaire.entities.PrescriptionDeMedicament;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.util.List;

public interface IPrescriptionDeMedicamentRepo extends CRUDRepository<PrescriptionDeMedicament, Long> {
    List<PrescriptionDeMedicament> findByOrdonnance(Long ordonnanceId) throws DaoException;
}
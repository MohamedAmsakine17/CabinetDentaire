package ma.CabinetDentaire.repository.api;

import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.util.List;

public interface IConsultationRepo extends CRUDRepository<Consultation,Long> {
    List<Consultation> findByDossier(DossierMedicale dossier) throws DaoException;
}
package ma.CabinetDentaire.repository.api;

import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.time.LocalDate;
import java.util.List;

public interface IRendezVousRepo extends CRUDRepository<RendezVous,Long> {
     List<RendezVous> findByDossier(DossierMedicale dossier)throws DaoException;
     List<RendezVous> findByDate(LocalDate date) throws DaoException;
     RendezVous findByConsultation(Consultation consultation)throws DaoException;
}

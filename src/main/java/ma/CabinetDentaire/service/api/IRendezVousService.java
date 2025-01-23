package ma.CabinetDentaire.service.api;

import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.entities.enums.TypeRDV;
import ma.CabinetDentaire.service.exceptions.PatientException;
import ma.CabinetDentaire.service.exceptions.RendezVousException;

import java.time.LocalDate;
import java.util.List;

public interface IRendezVousService {
    List<RendezVous> getAll() throws RendezVousException;
    List<RendezVous> filterByTypeRDV(TypeRDV typeRDV) throws RendezVousException;
    List<RendezVous> filterByDate(LocalDate date) throws RendezVousException;
    List<RendezVous> filterByDossier(Long dossierId) throws RendezVousException;
    RendezVous filterById(Long id) throws RendezVousException;
    RendezVous createRendezVous(RendezVous rendezVous) throws RendezVousException;
    void updateRendezVous(RendezVous rendezVous) throws RendezVousException;
    void deleteRendezVousById(Long id) throws RendezVousException;
}

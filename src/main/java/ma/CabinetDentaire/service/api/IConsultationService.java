package ma.CabinetDentaire.service.api;

import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.entities.enums.TypeConsultation;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.exceptions.ConsultationException;
import ma.CabinetDentaire.service.exceptions.RendezVousException;

import java.time.LocalDate;
import java.util.List;

public interface IConsultationService {
    Consultation createConsultation(Consultation consultation) throws ConsultationException;
    Consultation findById(Long id) throws ConsultationException;
    List<Consultation> findAll() throws ConsultationException;
    void updateConsultation(Consultation consultation) throws ConsultationException;
    void deleteConsultationById(Long id) throws ConsultationException;
    List<Consultation> findByDossier(Long dossierId)throws ConsultationException;
}

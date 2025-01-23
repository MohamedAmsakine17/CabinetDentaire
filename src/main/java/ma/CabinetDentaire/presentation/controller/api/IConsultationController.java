package ma.CabinetDentaire.presentation.controller.api;

import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.enums.TypeConsultation;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.exceptions.ConsultationException;

import java.time.LocalDate;
import java.util.List;

public interface IConsultationController {


     Consultation createConsultation(TypeConsultation type, LocalDate date, DossierMedicale dossierMedicale) throws ConsultationException ;
     Consultation findConsultationById(Long id) throws ConsultationException;
     List<Consultation> getAllConsultations() throws ConsultationException ;
     List<Consultation> getAllConsultationsByDossier(Long id ) ;
     void updateConsultation(Long idConsultation,TypeConsultation type, LocalDate date, DossierMedicale dossierMedicale) throws ConsultationException ;
     void updateConsultation(Consultation consultation);
     void deleteConsultationById(Long id) throws ConsultationException ;
     List<Consultation> filterByDossier(Long dossierId) throws ConsultationException ;
}

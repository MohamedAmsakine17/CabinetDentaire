package ma.CabinetDentaire.presentation.controller.api;

import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.Ordonnance;
import ma.CabinetDentaire.service.exceptions.OrdonnanceException;

import java.time.LocalDate;
import java.util.List;

public interface IOrdonnanceController {
    void createOrdonnancePanel(Consultation consultation);
    void modifierOrdonnancePanel(Ordonnance ordonnance);
    void displayOrddonnaceById(Long id);
    void createOrdonnance(LocalDate date, Consultation consultation) throws OrdonnanceException;
    Ordonnance findById(Long id) throws OrdonnanceException;
    void findAll() throws OrdonnanceException;
    void findAllByDossierId(Long dossierId) throws OrdonnanceException;
    Ordonnance findByConsultation(Long consultationId) throws OrdonnanceException;
    void updateOrdonnance(Ordonnance ordonnance) throws OrdonnanceException;
    void deleteOrdonnanceById(Long id,Long dossierId) throws OrdonnanceException;
}
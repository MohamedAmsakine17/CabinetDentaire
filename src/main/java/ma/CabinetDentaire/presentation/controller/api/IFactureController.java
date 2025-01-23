package ma.CabinetDentaire.presentation.controller.api;

import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.Facture;
import ma.CabinetDentaire.entities.SituationFinanciere;
import ma.CabinetDentaire.entities.enums.TypePaiement;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.exceptions.FactureException;

import java.time.LocalDate;
import java.util.List;

public interface IFactureController {
    void createFacturePanel(Consultation consultation) throws DaoException;
    void displayFactureById(Long id) throws FactureException;
    void findAll() throws FactureException;
    void createFacture(Double montantTotal, Double montantPaye, LocalDate dateFacturation, Long situationFinanciereId, TypePaiement typePaiement, Consultation consultation) throws FactureException, DaoException;
    void updateFacture(Facture facture) throws FactureException;
    void deleteFactureById(Long id) throws FactureException;
    void findByConsultation(Long consultationId) throws FactureException;
}
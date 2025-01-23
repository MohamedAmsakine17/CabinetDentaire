package ma.CabinetDentaire.presentation.controller.api;

import ma.CabinetDentaire.entities.SituationFinanciere;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.service.exceptions.SituationFinanciereException;

import java.time.LocalDate;

public interface ISituationFinanciereController {
    void createSituationFinancierePanel();
    void displaySituationFinanciereById(Long id) throws SituationFinanciereException;
    void findAll() throws SituationFinanciereException;
    void createSituationFinanciere(Double montantGlobalePaye, Double montantGlobaleRestant, LocalDate dateCreation, Long dossierId) throws SituationFinanciereException, DaoException;
    void updateSituationFinanciere(SituationFinanciere situationFinanciere) throws SituationFinanciereException;
    void deleteSituationFinanciereById(Long id) throws SituationFinanciereException;
    void findByDossierMedicale(Long dossierId) throws SituationFinanciereException;
}
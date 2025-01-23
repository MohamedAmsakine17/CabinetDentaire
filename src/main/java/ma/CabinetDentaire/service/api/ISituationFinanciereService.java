package ma.CabinetDentaire.service.api;

import ma.CabinetDentaire.entities.SituationFinanciere;
import ma.CabinetDentaire.service.exceptions.SituationFinanciereException;

import java.util.List;

public interface ISituationFinanciereService {
    SituationFinanciere createSituationFinanciere(SituationFinanciere situationFinanciere) throws SituationFinanciereException;
    SituationFinanciere findById(Long id) throws SituationFinanciereException;
    List<SituationFinanciere> findAll() throws SituationFinanciereException;
    void updateSituationFinanciere(SituationFinanciere situationFinanciere) throws SituationFinanciereException;
    void deleteSituationFinanciereById(Long id) throws SituationFinanciereException;
    SituationFinanciere findByDossierMedicale(Long dossierId) throws SituationFinanciereException;
}
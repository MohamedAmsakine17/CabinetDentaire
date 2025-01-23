package ma.CabinetDentaire.service;

import ma.CabinetDentaire.entities.SituationFinanciere;
import ma.CabinetDentaire.repository.api.ISituationFinanciereRepo;
import ma.CabinetDentaire.service.api.ISituationFinanciereService;
import ma.CabinetDentaire.service.exceptions.SituationFinanciereException;

import java.util.List;

public class SituationFinanciereService implements ISituationFinanciereService {
    private final ISituationFinanciereRepo situationFinanciereRepo;

    public SituationFinanciereService(ISituationFinanciereRepo situationFinanciereRepo) {
        this.situationFinanciereRepo = situationFinanciereRepo;
    }

    @Override
    public SituationFinanciere createSituationFinanciere(SituationFinanciere situationFinanciere) throws SituationFinanciereException {
        try {
            return situationFinanciereRepo.save(situationFinanciere);
        } catch (Exception e) {
            throw new SituationFinanciereException("Error creating SituationFinanciere: " + e.getMessage());
        }
    }

    @Override
    public SituationFinanciere findById(Long id) throws SituationFinanciereException {
        try {
            return situationFinanciereRepo.findById(id);
        } catch (Exception e) {
            throw new SituationFinanciereException("Error finding SituationFinanciere: " + e.getMessage());
        }
    }

    @Override
    public List<SituationFinanciere> findAll() throws SituationFinanciereException {
        try {
            return situationFinanciereRepo.findAll();
        } catch (Exception e) {
            throw new SituationFinanciereException("Error fetching all SituationFinancieres: " + e.getMessage());
        }
    }

    @Override
    public void updateSituationFinanciere(SituationFinanciere situationFinanciere) throws SituationFinanciereException {
        try {
            situationFinanciereRepo.update(situationFinanciere);
        } catch (Exception e) {
            throw new SituationFinanciereException("Error updating SituationFinanciere: " + e.getMessage());
        }
    }

    @Override
    public void deleteSituationFinanciereById(Long id) throws SituationFinanciereException {
        try {
            situationFinanciereRepo.deleteById(id);
        } catch (Exception e) {
            throw new SituationFinanciereException("Error deleting SituationFinanciere: " + e.getMessage());
        }
    }

    @Override
    public SituationFinanciere findByDossierMedicale(Long dossierId) throws SituationFinanciereException {
        try {
            return situationFinanciereRepo.findByDossierMedicale(dossierId);
        } catch (Exception e) {
            throw new SituationFinanciereException("Error finding SituationFinanciere for DossierMedicale: " + e.getMessage());
        }
    }
}
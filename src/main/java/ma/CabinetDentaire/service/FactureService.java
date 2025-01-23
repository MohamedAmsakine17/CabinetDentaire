package ma.CabinetDentaire.service;

import ma.CabinetDentaire.entities.Facture;
import ma.CabinetDentaire.repository.api.IFactureRepo;
import ma.CabinetDentaire.service.api.IFactureService;
import ma.CabinetDentaire.service.exceptions.FactureException;

import java.util.List;

public class FactureService implements IFactureService {
    private final IFactureRepo factureRepo;

    public FactureService(IFactureRepo factureRepo) {
        this.factureRepo = factureRepo;
    }

    @Override
    public Facture createFacture(Facture facture) throws FactureException {
        try {
            return factureRepo.save(facture);
        } catch (Exception e) {
            throw new FactureException("Error creating Facture: " + e.getMessage());
        }
    }

    @Override
    public Facture findById(Long id) throws FactureException {
        try {
            return factureRepo.findById(id);
        } catch (Exception e) {
            throw new FactureException("Error finding Facture: " + e.getMessage());
        }
    }

    @Override
    public List<Facture> findAll() throws FactureException {
        try {
            return factureRepo.findAll();
        } catch (Exception e) {
            throw new FactureException("Error fetching all Factures: " + e.getMessage());
        }
    }

    @Override
    public void updateFacture(Facture facture) throws FactureException {
        try {
            factureRepo.update(facture);
        } catch (Exception e) {
            throw new FactureException("Error updating Facture: " + e.getMessage());
        }
    }

    @Override
    public void deleteFactureById(Long id) throws FactureException {
        try {
            factureRepo.deleteById(id);
        } catch (Exception e) {
            throw new FactureException("Error deleting Facture: " + e.getMessage());
        }
    }

    @Override
    public List<Facture> findByConsultation(Long consultationId) throws FactureException {
        try {
            return factureRepo.findByConsultation(consultationId);
        } catch (Exception e) {
            throw new FactureException("Error fetching Factures by Consultation: " + e.getMessage());
        }
    }
}
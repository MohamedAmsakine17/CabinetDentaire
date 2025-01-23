package ma.CabinetDentaire.service;

import ma.CabinetDentaire.entities.Acte;
import ma.CabinetDentaire.repository.api.IActeRepo;
import ma.CabinetDentaire.service.api.IActeService;
import ma.CabinetDentaire.service.exceptions.ActeException;

import java.util.List;

public class ActeService implements IActeService {
    private final IActeRepo acteRepo;

    public ActeService(IActeRepo acteRepo) {
        this.acteRepo = acteRepo;
    }

    @Override
    public Acte createActe(Acte acte) throws ActeException {
        try {
            return acteRepo.save(acte);
        } catch (Exception e) {
            throw new ActeException("Error creating Acte: " + e.getMessage());
        }
    }

    @Override
    public Acte findById(Long id) throws ActeException {
        try {
            return acteRepo.findById(id);
        } catch (Exception e) {
            throw new ActeException("Error finding Acte: " + e.getMessage());
        }
    }

    @Override
    public Acte findByLibelle(String libelle) throws ActeException {
        try {
            return acteRepo.findByLibelle(libelle);
        } catch (Exception e) {
            throw new ActeException("Error finding Acte: " + e.getMessage());
        }
    }

    @Override
    public List<Acte> findAll() throws ActeException {
        try {
            return acteRepo.findAll();
        } catch (Exception e) {
            throw new ActeException("Error fetching all Actes: " + e.getMessage());
        }
    }

    @Override
    public void updateActe(Acte acte) throws ActeException {
        try {
            acteRepo.update(acte);
        } catch (Exception e) {
            throw new ActeException("Error updating Acte: " + e.getMessage());
        }
    }

    @Override
    public void deleteActeById(Long id) throws ActeException {
        try {
            acteRepo.deleteById(id);
        } catch (Exception e) {
            throw new ActeException("Error deleting Acte: " + e.getMessage());
        }
    }
}
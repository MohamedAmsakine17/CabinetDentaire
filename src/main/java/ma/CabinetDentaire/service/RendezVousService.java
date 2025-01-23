package ma.CabinetDentaire.service;

import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.entities.enums.TypeRDV;
import ma.CabinetDentaire.repository.api.IRendezVousRepo;
import ma.CabinetDentaire.service.api.IRendezVousService;
import ma.CabinetDentaire.service.exceptions.RendezVousException;

import java.time.LocalDate;
import java.util.List;

public class RendezVousService implements IRendezVousService {
    private final IRendezVousRepo rendezVousRepo;

    public RendezVousService(IRendezVousRepo rendezVousRepo) {
        this.rendezVousRepo = rendezVousRepo;
    }

    @Override
    public List<RendezVous> getAll() throws RendezVousException {
        try {
            return rendezVousRepo.findAll();
        } catch (Exception e) {
            throw new RendezVousException("Error fetching all RendezVous: " + e.getMessage());
        }
    }

    @Override
    public List<RendezVous> filterByTypeRDV(TypeRDV typeRDV) throws RendezVousException {
        try {
            return rendezVousRepo.findAll()
                    .stream()
                    .filter(rdv -> rdv.getTypeRDV() == typeRDV)
                    .toList();
        } catch (Exception e) {
            throw new RendezVousException("Error filtering RendezVous by type: " + e.getMessage());
        }
    }

    @Override
    public List<RendezVous> filterByDate(LocalDate date) throws RendezVousException {
        try {
            return rendezVousRepo.findAll()
                    .stream()
                    .filter(rdv -> rdv.getDateRDV().equals(date))
                    .toList();
        } catch (Exception e) {
            throw new RendezVousException("Error filtering RendezVous by date: " + e.getMessage());
        }
    }

    @Override
    public List<RendezVous> filterByDossier(Long dossierId) throws RendezVousException {
        try {
            return rendezVousRepo.findAll()
                    .stream()
                    .filter(rdv -> rdv.getDossier().getId().equals(dossierId))
                    .toList();
        } catch (Exception e) {
            throw new RendezVousException("Error filtering RendezVous by dossier: " + e.getMessage());
        }
    }

    @Override
    public RendezVous filterById(Long id) throws RendezVousException {
        try {
            return rendezVousRepo.findById(id);
        } catch (Exception e) {
            throw new RendezVousException("Error creating RendezVous: " + e.getMessage());
        }
    }

    @Override
    public RendezVous createRendezVous(RendezVous rendezVous) throws RendezVousException {
        try {
            return rendezVousRepo.save(rendezVous);
        } catch (Exception e) {
            throw new RendezVousException("Error creating RendezVous: " + e.getMessage());
        }
    }

    @Override
    public void updateRendezVous(RendezVous rendezVous) throws RendezVousException {
        try {
            rendezVousRepo.update(rendezVous);
        } catch (Exception e) {
            throw new RendezVousException("Error updating RendezVous: " + e.getMessage());
        }
    }

    @Override
    public void deleteRendezVousById(Long id) throws RendezVousException {
        try {
            rendezVousRepo.deleteById(id);
        } catch (Exception e) {
            throw new RendezVousException("Error deleting RendezVous: " + e.getMessage());
        }
    }

    public RendezVous filterByConsultation(Consultation consultation) throws RendezVousException {
        try {
            return rendezVousRepo.findByConsultation(consultation);
        } catch (Exception e) {
            throw new RendezVousException("Error creating RendezVous: " + e.getMessage());
        }
    }
}

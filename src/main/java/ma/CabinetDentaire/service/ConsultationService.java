package ma.CabinetDentaire.service;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.entities.enums.TypeConsultation;
import ma.CabinetDentaire.repository.api.IConsultationRepo;
import ma.CabinetDentaire.repository.api.IRendezVousRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.repository.fileDB_impl.RendezVousRepo;
import ma.CabinetDentaire.service.api.IConsultationService;
import ma.CabinetDentaire.service.api.IRendezVousService;
import ma.CabinetDentaire.service.exceptions.ConsultationException;
import ma.CabinetDentaire.service.exceptions.RendezVousException;

import java.time.LocalDate;
import java.util.List;

public class ConsultationService implements IConsultationService {
    private final IConsultationRepo consultationRepo;

    public ConsultationService(IConsultationRepo consultationRepo) {
        this.consultationRepo = consultationRepo;
    }

    @Override
    public Consultation createConsultation(Consultation consultation) throws ConsultationException {
        try {
            return consultationRepo.save(consultation);
        } catch (Exception e) {
            throw new ConsultationException("Error creating Consultation: " + e.getMessage());
        }
    }

    @Override
    public Consultation findById(Long id) throws ConsultationException {
        try {
            return consultationRepo.findById(id);
        } catch (Exception e) {
            throw new ConsultationException("Error finding Consultation: " + e.getMessage());
        }
    }

    @Override
    public List<Consultation> findAll() throws ConsultationException {
        try {
            return consultationRepo.findAll();
        } catch (Exception e) {
            throw new ConsultationException("Error fetching all Consultations: " + e.getMessage());
        }
    }

    @Override
    public void updateConsultation(Consultation consultation) throws ConsultationException {
        try {
            consultationRepo.update(consultation);
        } catch (Exception e) {
            throw new ConsultationException("Error updating Consultation: " + e.getMessage());
        }
    }

    @Override
    public void deleteConsultationById(Long id) throws ConsultationException {
        try {
            RendezVous rendezVous = AppFactory.getRendezVousService().filterByConsultation(consultationRepo.findById(id));
            rendezVous.setConsultation(null);
            AppFactory.getRendezVousController().updateRendezVous(rendezVous);
            consultationRepo.deleteById(id);
        } catch (Exception e) {
            throw new ConsultationException("Error deleting Consultation: " + e.getMessage());
        }
    }

    @Override
    public List<Consultation> findByDossier(Long dossierId) throws ConsultationException {
        try {
            return consultationRepo.findAll()
                    .stream()
                    .filter(consultation -> consultation.getDossierMedicale().getId().equals(dossierId))
                    .toList();
        } catch (Exception e) {
            throw new RendezVousException("Error filtering RendezVous by dossier: " + e.getMessage());
        }
    }
}
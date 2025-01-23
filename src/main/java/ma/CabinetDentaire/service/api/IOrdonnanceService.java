package ma.CabinetDentaire.service.api;

import ma.CabinetDentaire.entities.Ordonnance;
import ma.CabinetDentaire.service.exceptions.OrdonnanceException;

import java.time.LocalDate;
import java.util.List;

public interface IOrdonnanceService {
    Ordonnance createOrdonnance(Ordonnance ordonnance) throws OrdonnanceException;
    Ordonnance findById(Long id) throws OrdonnanceException;
    List<Ordonnance> findAll() throws OrdonnanceException;
    List<Ordonnance> findAllByDossier(Long id) throws OrdonnanceException;
    Ordonnance findByConsultation(Long consultationId) throws OrdonnanceException;
    void updateOrdonnance(Ordonnance ordonnance) throws OrdonnanceException;
    void deleteOrdonnanceById(Long id) throws OrdonnanceException;
}
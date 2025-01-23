package ma.CabinetDentaire.service.api;

import ma.CabinetDentaire.entities.Facture;
import ma.CabinetDentaire.service.exceptions.FactureException;

import java.util.List;

public interface IFactureService {
    Facture createFacture(Facture facture) throws FactureException;
    Facture findById(Long id) throws FactureException;
    List<Facture> findAll() throws FactureException;
    void updateFacture(Facture facture) throws FactureException;
    void deleteFactureById(Long id) throws FactureException;
    List<Facture> findByConsultation(Long consultationId) throws FactureException;
}
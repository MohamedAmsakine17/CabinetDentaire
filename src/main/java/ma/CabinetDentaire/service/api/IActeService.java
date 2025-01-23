package ma.CabinetDentaire.service.api;

import ma.CabinetDentaire.entities.Acte;
import ma.CabinetDentaire.service.exceptions.ActeException;

import java.util.List;

public interface IActeService {
    Acte createActe(Acte acte) throws ActeException;
    Acte findById(Long id) throws ActeException;
    Acte findByLibelle(String libelle) throws ActeException;
    List<Acte> findAll() throws ActeException;
    void updateActe(Acte acte) throws ActeException;
    void deleteActeById(Long id) throws ActeException;
}
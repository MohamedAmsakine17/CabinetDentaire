package ma.CabinetDentaire.presentation.controller.api;

import ma.CabinetDentaire.entities.Acte;
import ma.CabinetDentaire.entities.enums.CategorieActe;
import ma.CabinetDentaire.service.exceptions.ActeException;

public interface IActeController {
    void createActePanel();
    void displayActeById(Long id) throws ActeException;
    void findAll() throws ActeException;
    void createActe(String libelle, Double prixDeBase, CategorieActe categorie) throws ActeException;
    void updateActe(Acte acte) throws ActeException;
    void deleteActeById(Long id) throws ActeException;
}
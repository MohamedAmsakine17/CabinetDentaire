package ma.CabinetDentaire.presentation.controller.api;

import ma.CabinetDentaire.entities.Acte;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.InterventionMedecin;
import ma.CabinetDentaire.service.exceptions.InterventionMedecinException;

import java.util.List;

public interface IInterventionMedecinController {
    void createInterventionPanel(Consultation consultation) throws InterventionMedecinException;
    void updateInterventionPanel(InterventionMedecin interventionMedecin) throws InterventionMedecinException;
    void displayInterventionById(Long id) throws InterventionMedecinException;
    void findAll() throws InterventionMedecinException;
    void createIntervention(Long dent, Double prixPatient, String noteMedecin, Consultation consultation, Acte acte) throws InterventionMedecinException;
    void updateIntervention(InterventionMedecin intervention) throws InterventionMedecinException;
    void deleteInterventionById(Long id) throws InterventionMedecinException;
    void findByConsultation(Long consultationId) throws InterventionMedecinException;
}
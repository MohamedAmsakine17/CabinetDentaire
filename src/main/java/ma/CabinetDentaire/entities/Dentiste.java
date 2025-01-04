package ma.CabinetDentaire.entities;

import ma.CabinetDentaire.entities.enums.*;

import java.time.LocalDate;
import java.util.Map;

public class Dentiste extends Staff{
    private Specialite specialite;

    public Specialite getSpecialite() { return specialite; }

    public void setSpecialite(Specialite specialite) { this.specialite = specialite; }

    public Dentiste() {
        super();
    }

    public Dentiste(long id, String cin, String nom, String prenom, String adresse, String telephone,
                    String email, String photoDeProfile, LocalDate dataDeNaissance, Sexe sexe,
                    String username, String password, LocalDate dateDeCreation,
                    LocalDate dateDernierConnexion, LocalDate dateDeModification, Cabinet cabinetDeTravail,
                    StatusEmploye statusEmploye, Map<DayOfWeek, Disponibilite> disponibilite,
                    Double salaireDeBase, LocalDate dateRetourConge, Specialite specialite) {
        super(username, password, dateDeCreation, dateDernierConnexion, dateDeModification, id, cin, nom, prenom, adresse, telephone, email, photoDeProfile, dataDeNaissance, sexe,
                cabinetDeTravail, statusEmploye, disponibilite, salaireDeBase, dateRetourConge);
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return "Dentiste{" +
                "specialite=" + specialite +
                ", " + super.toString() +
                '}';
    }

}

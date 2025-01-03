package ma.CabinetDentaire.entities;

import ma.CabinetDentaire.entities.enums.DayOfWeek;
import ma.CabinetDentaire.entities.enums.Disponibilite;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.entities.enums.StatusEmploye;

import java.time.LocalDate;
import java.util.Map;

public class Staff extends Utilisateur{

    private Cabinet cabinetDeTravail;
    private StatusEmploye statusEmploye;
    private Map<DayOfWeek, Disponibilite> disponibilite;
    private Double salaireDeBase;
    private LocalDate dateRetourConge;

    public void setCabinetDeTravail(Cabinet cabinetDeTravail) { this.cabinetDeTravail = cabinetDeTravail; }
    public Cabinet getCabinetDeTravail() {return  cabinetDeTravail;}

    public Staff() {
        super();
    }
    public Staff(long id, String cin, String nom, String prenom, String adresse, String telephone, String email,
                 String photoDeProfile, LocalDate dataDeNaissance, Sexe sexe, String username, String password, LocalDate dateDeCreation, LocalDate dateDernierConnexion,
                 LocalDate dateDeModification, Cabinet cabinetDeTravail, StatusEmploye statusEmploye,
                 Map<DayOfWeek, Disponibilite> disponibilite, Double salaireDeBase, LocalDate dateRetourConge) {
        super(id, cin, nom, prenom, adresse, telephone, email, photoDeProfile, dataDeNaissance, sexe, username, password
                , dateDeCreation, dateDernierConnexion, dateDeModification);
        this.cabinetDeTravail = cabinetDeTravail;
        this.statusEmploye = statusEmploye;
        this.disponibilite = disponibilite;
        this.salaireDeBase = salaireDeBase;
        this.dateRetourConge = dateRetourConge;
    }

    public void prendreConge(LocalDate date, StatusEmploye statusEmploye) {

    }

    public void revenirTravailler(){
    }

    @Override
    public String toString() {
        return "Staff{" +
                "cabinetDeTravail=" + cabinetDeTravail +
                ", statusEmploye=" + statusEmploye +
                ", disponibilite=" + disponibilite +
                ", salaireDeBase=" + salaireDeBase +
                ", dateRetourConge=" + dateRetourConge +
                ", username=" + getUsername() +
                ", roles=" + getRoles() +
                ", nom=" + getNom() +
                ", prenom=" + getPrenom() +
                '}';
    }

}

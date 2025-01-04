package ma.CabinetDentaire.entities;

import ma.CabinetDentaire.entities.enums.DayOfWeek;
import ma.CabinetDentaire.entities.enums.Disponibilite;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.entities.enums.StatusEmploye;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Secretaire extends Staff{
    private Double prime;

    public void setPrime(Double prime) { if(prime <=0 ) return; this.prime = prime; }
    public Double getPrime() { return prime; }

    public Secretaire(){
        super();
    }

//    public Secretaire(long id, String cin, String nom, String prenom, String adresse, String telephone,
//                      String email, String photoDeProfile, LocalDate dataDeNaissance, Sexe sexe,
//                      String username, String password, LocalDate dateDeCreation,
//                      LocalDate dateDernierConnexion, LocalDate dateDeModification, Cabinet cabinetDeTravail,
//                      StatusEmploye statusEmploye, Map<DayOfWeek, Disponibilite> disponibilite,
//                      Double salaireDeBase, LocalDate dateRetourConge, Double prime) {
//        super(id, cin, nom, prenom, adresse, telephone, email, photoDeProfile, dataDeNaissance, sexe,
//                username, password, dateDeCreation, dateDernierConnexion, dateDeModification,
//                cabinetDeTravail, statusEmploye, disponibilite, salaireDeBase, dateRetourConge);
//        this.prime = prime;
//    }


    @Override
    public String toString() {
        return "Secretaire{" +
                "prime=" + prime +
                ", " + super.toString() +
                '}';
    }

}

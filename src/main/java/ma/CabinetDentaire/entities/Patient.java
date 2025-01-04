package ma.CabinetDentaire.entities;

import ma.CabinetDentaire.entities.enums.Sexe;

public class Patient extends  Personne{

    public Patient(Long id, String nom, String prenom , String email, String cin, Sexe sexe) {
        super(id, nom, prenom, email, cin, sexe);
    }
}

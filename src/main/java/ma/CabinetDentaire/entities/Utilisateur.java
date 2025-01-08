package ma.CabinetDentaire.entities;

import ma.CabinetDentaire.entities.enums.Sexe;

import java.time.LocalDate;
import java.util.List;

public class Utilisateur extends Personne {
    private String username;
    private String password;
    private List<Role> roles;
    private LocalDate dateDeCreation;
    private LocalDate dateDernierConnexion;
    private LocalDate dateDeModification;

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setDateDeCreation(LocalDate dateDeCreation) { this.dateDeCreation = dateDeCreation; }
    public void setDateDernierConnexion(LocalDate dateDernierConnexion) { this.dateDernierConnexion = dateDernierConnexion; }
    public void setDateDeModification(LocalDate dateDeModification) { this.dateDeModification = dateDeModification; }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<Role> getRoles() { return roles; }
    public LocalDate getDateDeCreation() { return dateDeCreation; }
    public LocalDate getDateDernierConnexion() { return dateDernierConnexion; }
    public LocalDate getDateDeModification() { return dateDeModification; }


    public Utilisateur() {
        super();
    }
    public Utilisateur(String username, String password){
        this.username = username;
        this.password = password;
    }
    public Utilisateur(String username, String password,
                       LocalDate dateDeCreation, LocalDate dateDernierConnexion, LocalDate dateDeModification,long id, String cin, String nom, String prenom, String adresse, String telephone, String email,
                       String photoDeProfile, LocalDate dataDeNaissance, Sexe sexe) {

        super(id, nom, prenom,cin, adresse, telephone, email, photoDeProfile, dataDeNaissance, sexe);
        this.username = username;
        this.password = password;
        this.dateDeCreation = dateDeCreation;
        this.dateDernierConnexion = dateDernierConnexion;
        this.dateDeModification = dateDeModification;
    }

    public void ajouterRole(Role role){
        roles.add(role);
    }

    public boolean aPrivileger(String privilege){
        for (Role role : getRoles()) {
            for (String priv: role.getPrivileges()){
                if(priv == privilege){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", dateDeCreation=" + dateDeCreation +
                ", dateDernierConnexion=" + dateDernierConnexion +
                ", dateDeModification=" + dateDeModification +
                '}';
    }
}

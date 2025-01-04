package ma.CabinetDentaire.entities;

import ma.CabinetDentaire.entities.enums.Sexe;

import java.time.LocalDate;

public class Personne {
    private Long id;
    private String cin;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private String photoDeProfile;
    private LocalDate dataDeNaissance;
    private Sexe sexe;

    public void setId(long id) {this.id = id;}
    public void setCin(String cin) {this.cin = cin;}
    public void setNom(String nom) {this.nom = nom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}
    public void setAdresse(String adresse) {this.adresse = adresse;}
    public void setTelephone(String telephone) {this.telephone = telephone;}
    public void setEmail(String email) {this.email = email;}
    public void setPhotoDeProfile(String photoDeProfile) {
        if(photoDeProfile == null || photoDeProfile.isEmpty()){
            this.photoDeProfile = "default";
            return;
        }
        this.photoDeProfile = photoDeProfile;
    }
    public void setDataDeNaissance(LocalDate dataDeNaissance) {this.dataDeNaissance = dataDeNaissance;}
    public void setSexe(Sexe sexe) {this.sexe = sexe;}

    public Long getId() {return id;}
    public String getCin() {return cin;}
    public String getNom() {return nom;}
    public String getPrenom() {return prenom;}
    public String getAdresse() {return adresse;}
    public String getTelephone() {return telephone;}
    public String getEmail() {return email;}
    public String getPhotoDeProfile() {return photoDeProfile;}
    public LocalDate getDataDeNaissance() {return dataDeNaissance;}
    public Sexe getSexe() {return sexe;}


    public Personne() {}
    public Personne(Long id, String nom, String prenom , String email, String cin, Sexe sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.cin = cin;
        this.sexe = sexe;
    }


    public Personne(Long id, String nom, String prenom, String adresse, String telephone, String email, String photoDeProfile, LocalDate dataDeNaissance ,Sexe sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.dataDeNaissance = dataDeNaissance;
        this.sexe = sexe;

        setPhotoDeProfile(photoDeProfile);
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", cin='" + cin + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", photoDeProfile='" + photoDeProfile + '\'' +
                ", dataDeNaissance=" + dataDeNaissance +
                ", sexe=" + sexe +
                '}';
    }
}

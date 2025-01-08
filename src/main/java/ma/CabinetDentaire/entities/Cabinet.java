package ma.CabinetDentaire.entities;

import java.util.List;

public class Cabinet {
    private Long id;
    private String nom;
    private String logo;
    private String email;
    private String adresse;
    private String telephone1;
    private String telephone2;
    private String tel;
    private List<Staff> staff;

    public Cabinet() {}
    public Cabinet(Long id, String nom, String logo, String email, String adresse, String telephone1, String telephone2, String tel) {
        this.id = id;
        this.nom = nom;
        this.logo = logo;
        this.email = email;
        this.adresse = adresse;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        this.tel = tel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    private void addStaff(Staff staff) {
        this.staff.add(staff);
    }
}

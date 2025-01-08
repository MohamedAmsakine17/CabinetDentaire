package ma.CabinetDentaire.entities;

import java.util.List;
import ma.CabinetDentaire.entities.enums.GroupeSanguin;
import ma.CabinetDentaire.entities.enums.Mutuelle;
import ma.CabinetDentaire.entities.enums.Sexe;

import java.time.LocalDate;

public class Patient extends  Personne{
    GroupeSanguin groupeSanguin;
    Mutuelle mutuelle;
    List<HistoriqueMedicale> historiqueMedicale;
    String profession;
    DossierMedicale dossierMedicale;

    public Patient() {super();}
    public Patient(Long id, String nom, String prenom , String email, String cin, Sexe sexe) {
        super(id, nom, prenom, email, cin, sexe);
    }
    public Patient(Long id, String nom, String prenom,String cin, String adresse, String telephone,
                   String email, String photoDeProfile, LocalDate dataDeNaissance , Sexe sexe,
                   GroupeSanguin groupeSanguin,Mutuelle mutuelle, String profession, DossierMedicale dossierMedicale) {
        super(id,nom,prenom,cin,adresse,telephone,email,photoDeProfile,dataDeNaissance,sexe);
        this.groupeSanguin = groupeSanguin;
        this.mutuelle = mutuelle;
        this.profession = profession;
        this.dossierMedicale = dossierMedicale;
    }

    public GroupeSanguin getGroupeSanguin() { return groupeSanguin; }

    public void setGroupeSanguin(GroupeSanguin groupeSanguin) { this.groupeSanguin = groupeSanguin; }

    public Mutuelle getMutuelle() { return mutuelle; }

    public void setMutuelle(Mutuelle mutuelle) { this.mutuelle = mutuelle; }

    public String getProfession() { return profession; }

    public void setProfession(String profession) { this.profession = profession; }

    public DossierMedicale getDossierMedicale() { return dossierMedicale; }

    public void setDossierMedicale(DossierMedicale dossierMedicale) { this.dossierMedicale = dossierMedicale; }

    public List<HistoriqueMedicale> getHistoriqueMedicale() { return historiqueMedicale; }

    public void addHistoriqueMedicale(HistoriqueMedicale historiqueMedicale) {
        this.historiqueMedicale.add(historiqueMedicale);
    }
}

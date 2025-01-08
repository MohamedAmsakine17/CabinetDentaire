package ma.CabinetDentaire.entities.enums;

public enum CategorieHistoriqueMedicale {
    CONTRE_INDICATION("Contre-indication",Risque.ELEVE),
    MALADIE_CHRONIQUE("Maladie chronique",Risque.MOYEN),
    MALADIE_HEREDITAIRE("Maladie héréditaire",Risque.FAIBLE),
    ALLERGIE("Allergie",Risque.ELEVE),
    AUTRE("Autre",Risque.INCONNU);

    private final String description;
    private final Risque risqueAssocie;

    CategorieHistoriqueMedicale(String description, Risque risqueAssocie){
        this.description = description;
        this.risqueAssocie = risqueAssocie;
    }

    public String getDescription(){
        return description;
    }

    public Risque getRisqueAssocie(){
        return risqueAssocie;
    }
}

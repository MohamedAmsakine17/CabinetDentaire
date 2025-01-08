package ma.CabinetDentaire.entities.enums;

public enum CategorieActe {
    PREVENTION("Prévention"),
    PROTHESES_DENTAIRES("Prothèses dentaires"),
    ORTHODONTIE("Orthodontie"),
    IMPLANTOLOGIE("Implantologie"),
    CHIRURGIE("Chirurgie"),
    SOINS_DES_TISSUS_MOUS("Soins des tissus mous"),
    SOINS_DES_CARIES("Soins des caries"),
    DIAGNOSTIC("Diagnostic");

    private final String libelle;

    CategorieActe(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}

package ma.CabinetDentaire.entities;

import java.util.List;

public class Caisse {
    private Long id;
    private Double recetteDuMois;
    private Double recetteDeLAnnee;
    private Double recetteDuJours;
    private Cabinet cabinet;
    private List<SituationFinanciere> situationFinancieres;

    public Caisse() {}
    public Caisse(Long id, Double recetteDuMois, Double recetteDeLAnnee, Double recetteDuJours, Cabinet cabinet) {
        this.id = id;
        this.recetteDuMois = recetteDuMois;
        this.recetteDeLAnnee = recetteDeLAnnee;
        this.recetteDuJours = recetteDuJours;
        this.cabinet = cabinet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRecetteDuMois() {
        return recetteDuMois;
    }

    public void setRecetteDuMois(Double recetteDuMois) {
        this.recetteDuMois = recetteDuMois;
    }

    public Double getRecetteDeLAnnee() {
        return recetteDeLAnnee;
    }

    public void setRecetteDeLAnnee(Double recetteDeLAnnee) {
        this.recetteDeLAnnee = recetteDeLAnnee;
    }

    public Double getRecetteDuJours() {
        return recetteDuJours;
    }

    public void setRecetteDuJours(Double recetteDuJours) {
        this.recetteDuJours = recetteDuJours;
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    public List<SituationFinanciere> getSituationFinancieres() {
        return situationFinancieres;
    }

    public void addSituationFinancieres(SituationFinanciere situationFinanciere) {
        this.situationFinancieres.add(situationFinanciere);
    }
}

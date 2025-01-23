package ma.CabinetDentaire.presentation.view.palette.panels.dashboard_panels;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Caisse;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.Facture;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class DashboardHeader extends JPanel {
    private Theme currentTheme;
    Long consultationDeJour;
    Long consultationDeMois;
    Long consultationDAnne;


    public void _init() throws DaoException {
        setLayout(new GridLayout(2,3,20,20));
        setOpaque(false);

        Caisse caisse = Caisse.getInstance();
        List<Facture> factures = AppFactory.getFactureRepo().findAll();
        caisse.updateRecettes(factures);

        updateRecettes(AppFactory.getConsultationRepo().findAll());

        HeaderCard recetteDuJour = new HeaderCard(currentTheme,"Recette du jour",Caisse.getInstance().getRecetteDuJours().longValue(),"src/main/resources/images/header_recette_1.png",currentTheme.greenColor());
        HeaderCard recetteDuMois = new HeaderCard(currentTheme, "Recette du mois",Caisse.getInstance().getRecetteDuMois().longValue(),"src/main/resources/images/header_recette_2.png",currentTheme.yellowolor());
        HeaderCard recetteDuJourAnnee = new HeaderCard(currentTheme, "Recette du annee",Caisse.getInstance().getRecetteDeLAnnee().longValue(),"src/main/resources/images/header_recette_3.png",currentTheme.blueColor());

        HeaderCard nbrConsultationJour = new HeaderCard(currentTheme,"Nbr de Consultations du jour",consultationDeJour,"src/main/resources/images/header_consultation_1.png",currentTheme.greenColor());
        HeaderCard nbrConsultationMois = new HeaderCard(currentTheme, "Nbr de Consultations du mois",consultationDeMois,"src/main/resources/images/header_consultation_2.png",currentTheme.yellowolor());
        HeaderCard nbrConsultationAnnee = new HeaderCard(currentTheme, "Nbr de Consultations du annee",consultationDAnne,"src/main/resources/images/header_consultation_3.png",currentTheme.blueColor());

        add(recetteDuJour);
        add(recetteDuMois);
        add(recetteDuJourAnnee);

        add(nbrConsultationJour);
        add(nbrConsultationMois);
        add(nbrConsultationAnnee);
    }

    public DashboardHeader(Theme currentTheme) throws DaoException {
        this.currentTheme = currentTheme;
        _init();
    }

    public void updateRecettes(List<Consultation> consultations) {
        this.consultationDeJour = 0L;
        this.consultationDeMois = 0L;
        this.consultationDAnne =0L;

        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate startOfYear = today.withDayOfYear(1);

        for (Consultation consultation : consultations) {
            if (consultation.getDateConsultation().isEqual(today)) {
                this.consultationDeJour += 1;
            }
            if (consultation.getDateConsultation().isAfter(startOfMonth) || consultation.getDateConsultation().isEqual(startOfMonth)) {
                this.consultationDeMois += 1;
            }
            if (consultation.getDateConsultation().isAfter(startOfYear) || consultation.getDateConsultation().isEqual(startOfYear)) {
                this.consultationDAnne += 1;
            }
        }
    }
}

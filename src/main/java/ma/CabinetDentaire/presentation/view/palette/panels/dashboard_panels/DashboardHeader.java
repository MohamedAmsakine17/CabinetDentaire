package ma.CabinetDentaire.presentation.view.palette.panels.dashboard_panels;

import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class DashboardHeader extends JPanel {
    private Theme currentTheme;

    public void _init(){
        setLayout(new GridLayout(2,3,20,20));
        setOpaque(false);

        HeaderCard recetteDuJour = new HeaderCard(currentTheme,"Recette du jour",0L,"src/main/resources/images/header_recette_1.png",currentTheme.greenColor());
        HeaderCard recetteDuMois = new HeaderCard(currentTheme, "Recette du mois",0L,"src/main/resources/images/header_recette_2.png",currentTheme.yellowolor());
        HeaderCard recetteDuJourAnnee = new HeaderCard(currentTheme, "Recette du annee",0L,"src/main/resources/images/header_recette_3.png",currentTheme.blueColor());

        HeaderCard nbrConsultationJour = new HeaderCard(currentTheme,"Nbr de Consultations du jour",0L,"src/main/resources/images/header_consultation_1.png",currentTheme.greenColor());
        HeaderCard nbrConsultationMois = new HeaderCard(currentTheme, "Nbr de Consultations du mois",0L,"src/main/resources/images/header_consultation_2.png",currentTheme.yellowolor());
        HeaderCard nbrConsultationAnnee = new HeaderCard(currentTheme, "Nbr de Consultations du annee",0L,"src/main/resources/images/header_consultation_3.png",currentTheme.blueColor());

        add(recetteDuJour);
        add(recetteDuMois);
        add(recetteDuJourAnnee);

        add(nbrConsultationJour);
        add(nbrConsultationMois);
        add(nbrConsultationAnnee);
    }

    public DashboardHeader(Theme currentTheme){
        this.currentTheme = currentTheme;
        _init();
    }
}

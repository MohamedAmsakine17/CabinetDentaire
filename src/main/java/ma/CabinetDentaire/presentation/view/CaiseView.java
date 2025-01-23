package ma.CabinetDentaire.presentation.view;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Caisse;
import ma.CabinetDentaire.entities.Facture;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.palette.panels.dashboard_panels.DashboardHeader;
import ma.CabinetDentaire.presentation.view.palette.panels.dashboard_panels.HeaderCard;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CaiseView extends JPanel {
    private Theme currentTheme;

    private void _init() throws DaoException {
        // Initialize Caisse with all Factures
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        Caisse caisse = Caisse.getInstance();
        List<Facture> factures = AppFactory.getFactureRepo().findAll();
        caisse.updateRecettes(factures);

        MyLabel title = new MyLabel(currentTheme,"La Caisse",24,1);
        add(title, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        JPanel headerBodyPanel = new JPanel(new GridLayout(1,3,50,0));
        headerBodyPanel.setOpaque(false);

        HeaderCard recetteDuJour = new HeaderCard(currentTheme,"Recette du jour",caisse.getRecetteDuJours().longValue(),"src/main/resources/images/header_recette_1.png",currentTheme.greenColor());
        HeaderCard recetteDuMois = new HeaderCard(currentTheme, "Recette du mois",caisse.getRecetteDuMois().longValue(),"src/main/resources/images/header_recette_2.png",currentTheme.yellowolor());
        HeaderCard recetteDuJourAnnee = new HeaderCard(currentTheme, "Recette du annee",caisse.getRecetteDeLAnnee().longValue(),"src/main/resources/images/header_recette_3.png",currentTheme.blueColor());

        headerBodyPanel.add(recetteDuJour);
        headerBodyPanel.add(recetteDuMois);
        headerBodyPanel.add(recetteDuJourAnnee);
        bodyPanel.add(headerBodyPanel, BorderLayout.NORTH);



        JPanel mainFactureBodyContent = new JPanel(new GridLayout(8,1,0,5));
        mainFactureBodyContent.setOpaque(false);
        mainFactureBodyContent.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        //ID|MONTANT_TOTAL|MONTANT_RESTANT|MONTANT_PAYE|DATE_FACTURATION|SITUATION_FINANCIERE_ID|TYPE_PAIEMENT|CONSULTATION_ID
        JPanel headerTablePanel = new JPanel(new GridLayout(1,6));
        headerTablePanel.setBackground(currentTheme.greenColor());
        headerTablePanel.add(new Label());  // ID
        MyLabel montantTotaleColLabel = new MyLabel(currentTheme,"totale",18,1);
        headerTablePanel.add(montantTotaleColLabel);
        MyLabel montantRestantColLabel = new MyLabel(currentTheme,"Restant",18,1);
        headerTablePanel.add(montantRestantColLabel);
        MyLabel montantPayeColLabel = new MyLabel(currentTheme,"Paye",18,1);
        headerTablePanel.add(montantPayeColLabel);
        MyLabel dateColLabel = new MyLabel(currentTheme,"Date",18,1);
        headerTablePanel.add(dateColLabel);
        MyLabel typeColLabel = new MyLabel(currentTheme,"Type",18,1);
        headerTablePanel.add(typeColLabel);
        headerTablePanel.add(new Label());
        mainFactureBodyContent.add(headerTablePanel);

        AppFactory.getFactureRepo().findAll().forEach(facture -> {
            JPanel colTablePanel = new JPanel(new GridLayout(1,8));
            colTablePanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()), // Green bottom border
                    BorderFactory.createEmptyBorder(0, 0, 0, 0) // Padding
            ));
            colTablePanel.setBackground(currentTheme.fieldsBgColor());

            MyLabel interventionMedecinID = new MyLabel( currentTheme,"#" + facture.getId(),16,0);
            interventionMedecinID.setBorder(new EmptyBorder(0, 15, 0, 0));
            colTablePanel.add(interventionMedecinID);
            MyLabel interventionMedecinActeLibelle = new MyLabel(currentTheme,facture.getMontantTotal().toString(),16,0);
            colTablePanel.add(interventionMedecinActeLibelle);
            MyLabel interventionMedecinDent = new MyLabel(currentTheme,facture.getMontantRestant().toString(),16,0);
            colTablePanel.add(interventionMedecinDent);
            MyLabel interventionMedecinPrix = new MyLabel(currentTheme, facture.getMontantPaye().toString(),16,0);
            colTablePanel.add(interventionMedecinPrix);
            MyLabel interventionMedecinNote = new MyLabel(currentTheme, facture.getDataFacturation().toString(),16,0);
            colTablePanel.add(interventionMedecinNote);
            MyLabel typeFacturationLabel = new MyLabel(currentTheme, facture.getTypePaiement().toString(),16,0);
            colTablePanel.add(typeFacturationLabel);

            JPanel actionsButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            actionsButtonContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
            actionsButtonContainer.setOpaque(false);
            MyLabel updateBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/green_edit.png",26);
            updateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            updateBtn.setBorder(BorderFactory.createEmptyBorder(0,0,0,15));
            updateBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //AppFactory.getInterventionController().updateInterventionPanel(interventionMedecin);
                }
            });
            actionsButtonContainer.add(updateBtn);
            MyLabel deleteBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/red_delete.png",26);
            deleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            deleteBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getFactureController().deleteFactureById(facture.getId());
                }
            });
            actionsButtonContainer.add(deleteBtn);
            colTablePanel.add(actionsButtonContainer);

            mainFactureBodyContent.add(colTablePanel);
        });

        bodyPanel.add(mainFactureBodyContent, BorderLayout.CENTER);
        add(bodyPanel, BorderLayout.CENTER);
    }

    public CaiseView(Theme currentTheme) throws DaoException {
        this.currentTheme = currentTheme;
        _init();
    }
}

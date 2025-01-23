package ma.CabinetDentaire.presentation.view.dossier_medical;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Acte;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.Facture;
import ma.CabinetDentaire.entities.enums.CategorieActe;
import ma.CabinetDentaire.entities.enums.TypePaiement;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.form.FormGroup;
import ma.CabinetDentaire.presentation.view.palette.form.SelectFormGroup;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.List;

public class FacturePanel extends JPanel {
    Theme currentTheme;

    public FacturePanel(Theme currentTheme) {
        this.currentTheme = currentTheme;
    }

    public JPanel createFacture(Consultation consultation) throws DaoException {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Créer une Facture",22,1);
        mainPanel.add(title, BorderLayout.NORTH);

        if(AppFactory.getInterventionRepo().findByConsultation(consultation.getIdConsultation()).size() <= 0 ){
            MyLabel error = new MyLabel(currentTheme,"Aucun Intervention pour Ajouter le facture",15,0);
            mainPanel.add(error, BorderLayout.SOUTH);
            return mainPanel;
        }

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);

        JPanel formPanel = new JPanel(new GridLayout(3,2));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(30,0,60,120));

        AtomicReference<Double> montantTotal = new AtomicReference<>(0d);
        AppFactory.getInterventionRepo().findByConsultation(consultation.getIdConsultation()).forEach(interventionMedecin -> {
            montantTotal.updateAndGet(v -> v + interventionMedecin.getPrixPatient() + interventionMedecin.getActe().getPrixDeBase());
        });

        FormGroup montantTotalFormGroup  = new FormGroup(currentTheme,"Montant Total:","src/main/resources/images/icons/medicament_prix.png",montantTotal.toString());
        formPanel.add(montantTotalFormGroup );

        FormGroup montantPayeFormGroup   = new FormGroup(currentTheme,"Montant Payé:","src/main/resources/images/icons/medicament_prix.png","0");
        formPanel.add(montantPayeFormGroup);

        SelectFormGroup typeDePaimentFormGroup  = new SelectFormGroup(currentTheme,"Type de Paiment:","src/main/resources/images/edit_btn.png",new String[]{"ESPECE","CARTE_CREDIT","VIREMENT","CHEQUE","AUTRE"});
        formPanel.add(typeDePaimentFormGroup );


        JPanel buttonContainer = new JPanel(new BorderLayout()); buttonContainer.setOpaque(false);
        MyButton createButton = new MyButton(currentTheme,"Créer",18,currentTheme.greenColor(),currentTheme.greenHoverColor(),"src/main/resources/images/icons/white_add.png",22);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //Double montantTotal, Double montantPaye, LocalDate dateFacturation, Long situationFinanciereId, TypePaiement typePaiement, Consultation consultation

                Double montantTotal = Double.parseDouble(montantTotalFormGroup.getInputField().getText());
                Double montantPaye = Double.parseDouble(montantPayeFormGroup.getInputField().getText());
                LocalDate dateFacturation = LocalDate.now();
                Long situationFinanciereId = 0L;
                try {
                    situationFinanciereId = AppFactory.getSituationRepo().findByDossierMedicale(consultation.getDossierMedicale().getId()).getId();
                } catch (DaoException ex) {
                    throw new RuntimeException(ex);
                }
                TypePaiement typePaiement = typeDePaimentFormGroup.getSelectField().getSelectedItem().equals("ESPECE") ? TypePaiement.ESPECE
                        :typeDePaimentFormGroup.getSelectField().getSelectedItem().equals("CARTE_CREDIT") ? TypePaiement.CARTE_CREDIT
                        :typeDePaimentFormGroup.getSelectField().getSelectedItem().equals("VIREMENT") ? TypePaiement.VIREMENT
                        :typeDePaimentFormGroup.getSelectField().getSelectedItem().equals("CHEQUE") ? TypePaiement.CHEQUE
                        : TypePaiement.AUTRE;

                try {
                    AppFactory.getFactureController().createFacture(montantTotal,montantPaye,dateFacturation,situationFinanciereId,typePaiement,consultation);
                } catch (DaoException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buttonContainer.add(createButton, BorderLayout.WEST);

        bodyPanel.add(formPanel, BorderLayout.CENTER);
        bodyPanel.add(buttonContainer, BorderLayout.SOUTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    public JPanel allFacturesPanel(List<Facture> factures){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel headerMainPanel = new JPanel(new BorderLayout());
        headerMainPanel.setOpaque(false);
        MyLabel title = new MyLabel(currentTheme,"Les Facture de consultation",22,1);
        headerMainPanel.add(title, BorderLayout.WEST);
        MyLabel addActeButton = new MyLabel(currentTheme,"src/main/resources/images/icons/green_add.png",32);
        addActeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        headerMainPanel.add(addActeButton, BorderLayout.EAST);
        addActeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                if(interventionMedecins.size() > 0){
//                    AppFactory.getInterventionController().createInterventionPanel(interventionMedecins.get(0).getConsultation());
//                }
            }
        });

        mainPanel.add(headerMainPanel,BorderLayout.NORTH);

        JPanel mainBodyContent = new JPanel(new GridLayout(6,1,0,10));
        mainBodyContent.setOpaque(false);
        mainBodyContent.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
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
        mainBodyContent.add(headerTablePanel);

        factures.forEach(facture -> {
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

            mainBodyContent.add(colTablePanel);
        });

        mainPanel.add(mainBodyContent,BorderLayout.CENTER);

        return mainPanel;
    }
}

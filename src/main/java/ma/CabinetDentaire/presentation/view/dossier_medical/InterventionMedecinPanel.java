package ma.CabinetDentaire.presentation.view.dossier_medical;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Acte;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.InterventionMedecin;
import ma.CabinetDentaire.entities.enums.CategorieActe;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.form.FormGroup;
import ma.CabinetDentaire.presentation.view.palette.form.SelectFormGroup;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InterventionMedecinPanel extends JLabel {
    Theme currentTheme;
    public InterventionMedecinPanel(Theme currentTheme) {
        this.currentTheme = currentTheme;
    }

    public JPanel interventionPanel(InterventionMedecin interventionMedecin) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Intervention",22,1);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel listPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
        listPanel.setOpaque(false);

        return mainPanel;
    }

    public JPanel interventionAllPanels(List<InterventionMedecin> interventionMedecins) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel headerMainPanel = new JPanel(new BorderLayout());
        headerMainPanel.setOpaque(false);
        MyLabel title = new MyLabel(currentTheme,"Les Intervation Medecin",22,1);
        headerMainPanel.add(title, BorderLayout.WEST);
        MyLabel addActeButton = new MyLabel(currentTheme,"src/main/resources/images/icons/green_add.png",32);
        addActeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        headerMainPanel.add(addActeButton, BorderLayout.EAST);
        addActeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(interventionMedecins.size() > 0){
                    AppFactory.getInterventionController().createInterventionPanel(interventionMedecins.get(0).getConsultation());
                }
            }
        });

        mainPanel.add(headerMainPanel,BorderLayout.NORTH);

        JPanel mainBodyContent = new JPanel(new GridLayout(7,1,0,10));
        mainBodyContent.setOpaque(false);
        mainBodyContent.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));

        JPanel headerTablePanel = new JPanel(new GridLayout(1,6));
        headerTablePanel.setBackground(currentTheme.greenColor());
        headerTablePanel.add(new Label());
        MyLabel acteColLabel = new MyLabel(currentTheme,"Acte",18,1);
        headerTablePanel.add(acteColLabel);
        MyLabel dentColLabel = new MyLabel(currentTheme,"Dent",18,1);
        headerTablePanel.add(dentColLabel);
        MyLabel prixColLabel = new MyLabel(currentTheme,"Prix totla",18,1);
        headerTablePanel.add(prixColLabel);
        MyLabel noteColLabel = new MyLabel(currentTheme,"Note medecin",18,1);
        headerTablePanel.add(noteColLabel);
        headerTablePanel.add(new Label());
        mainBodyContent.add(headerTablePanel);

        interventionMedecins.forEach(interventionMedecin -> {
            JPanel colTablePanel = new JPanel(new GridLayout(1,6));
            colTablePanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()), // Green bottom border
                    BorderFactory.createEmptyBorder(0, 0, 0, 0) // Padding
            ));
            colTablePanel.setBackground(currentTheme.fieldsBgColor());

            MyLabel interventionMedecinID = new MyLabel( currentTheme,"#" + interventionMedecin.getId(),16,0);
            interventionMedecinID.setBorder(new EmptyBorder(0, 15, 0, 0));
            colTablePanel.add(interventionMedecinID);
            MyLabel interventionMedecinActeLibelle = new MyLabel(currentTheme,interventionMedecin.getActe().getLibelle(),16,0);
            colTablePanel.add(interventionMedecinActeLibelle);
            MyLabel interventionMedecinDent = new MyLabel(currentTheme,interventionMedecin.getDent().toString(),16,0);
            colTablePanel.add(interventionMedecinDent);
            Double prix = interventionMedecin.getActe().getPrixDeBase()+ interventionMedecin.getPrixPatient();
            MyLabel interventionMedecinPrix = new MyLabel(currentTheme, prix.toString(),16,0);
            colTablePanel.add(interventionMedecinPrix);
            MyLabel interventionMedecinNote = new MyLabel(currentTheme, interventionMedecin.getNoteMedecin(),16,0);
            colTablePanel.add(interventionMedecinNote);

            JPanel actionsButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            actionsButtonContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
            actionsButtonContainer.setOpaque(false);
            MyLabel updateBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/green_edit.png",26);
            updateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            updateBtn.setBorder(BorderFactory.createEmptyBorder(0,0,0,15));
            updateBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getInterventionController().updateInterventionPanel(interventionMedecin);
                }
            });
            actionsButtonContainer.add(updateBtn);
            MyLabel deleteBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/red_delete.png",26);
            deleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            deleteBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getInterventionController().deleteInterventionById(interventionMedecin.getId());
                }
            });
            actionsButtonContainer.add(deleteBtn);
            colTablePanel.add(actionsButtonContainer);

            mainBodyContent.add(colTablePanel);
        });

        mainPanel.add(mainBodyContent,BorderLayout.CENTER);

        return mainPanel;
    }

    public JPanel createIntervention(Consultation consultation){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Creation d'intervention medecin",22,1);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);

        JPanel formPanel = new JPanel(new GridLayout(3,2));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(30,0,60,120));
        //String libelle, Double prixDeBase, CategorieActe categorie

        FormGroup denteFormGroup = new FormGroup(currentTheme,"Dente:","src/main/resources/images/icons/min.png");
        formPanel.add(denteFormGroup);
        FormGroup prixDePatientFormGroup  = new FormGroup(currentTheme,"Prix de patient:","src/main/resources/images/icons/max.png");
        formPanel.add(prixDePatientFormGroup );
        String[] acteList = new String[AppFactory.getActeController().findAllActe().size()];
        for (int i = 0; i < acteList.length; i++) {
            acteList[i] = AppFactory.getActeController().findAllActe().get(i).getLibelle();
        }
        SelectFormGroup acteFormGroup  = new SelectFormGroup(currentTheme,"Actes:","src/main/resources/images/edit_btn.png",acteList);
        formPanel.add(acteFormGroup);
        FormGroup noteMedecinFormGroup  = new FormGroup(currentTheme,"Note Medecin:","src/main/resources/images/icons/max.png");
        formPanel.add(noteMedecinFormGroup );


        JPanel buttonContainer = new JPanel(new BorderLayout()); buttonContainer.setOpaque(false);
        MyButton createButton = new MyButton(currentTheme,"Créer",18,currentTheme.greenColor(),currentTheme.greenHoverColor(),"src/main/resources/images/icons/white_add.png",22);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //Long dent, Double prixPatient, String noteMedecin, Consultation consultation, Acte acte

                Long dent = Long.parseLong(denteFormGroup.getInputField().getText());
                Double prixPatient = Double.parseDouble(prixDePatientFormGroup.getInputField().getText());
                String noteMedecin = noteMedecinFormGroup.getInputField().getText();
                Acte acte = AppFactory.getActeService().findByLibelle(Objects.requireNonNull(acteFormGroup.getSelectField().getSelectedItem()).toString());
                System.out.println(acte.getPrixDeBase());

                AppFactory.getInterventionController().createIntervention(dent,prixPatient,noteMedecin,consultation,acte);
            }
        });
        buttonContainer.add(createButton, BorderLayout.WEST);

        bodyPanel.add(formPanel, BorderLayout.CENTER);
        bodyPanel.add(buttonContainer, BorderLayout.SOUTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    public JPanel updateIntervention(InterventionMedecin interventionMedecin){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Modifier Intervention medecin",22,1);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);

        JPanel formPanel = new JPanel(new GridLayout(3,2));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(30,0,60,120));
        //String libelle, Double prixDeBase, CategorieActe categorie

        FormGroup denteFormGroup = new FormGroup(currentTheme,"Dente:","src/main/resources/images/icons/min.png",interventionMedecin.getDent().toString());
        formPanel.add(denteFormGroup);
        FormGroup prixDePatientFormGroup  = new FormGroup(currentTheme,"Prix de patient:","src/main/resources/images/icons/max.png",interventionMedecin.getPrixPatient().toString());
        formPanel.add(prixDePatientFormGroup );
        String[] acteList = new String[AppFactory.getActeController().findAllActe().size()];
        for (int i = 0; i < acteList.length; i++) {
            acteList[i] = AppFactory.getActeController().findAllActe().get(i).getLibelle();
        }
        SelectFormGroup acteFormGroup  = new SelectFormGroup(currentTheme,"Actes:","src/main/resources/images/edit_btn.png",acteList);
        formPanel.add(acteFormGroup);
        FormGroup noteMedecinFormGroup  = new FormGroup(currentTheme,"Note Medecin:","src/main/resources/images/icons/max.png",interventionMedecin.getNoteMedecin());
        formPanel.add(noteMedecinFormGroup );


        JPanel buttonContainer = new JPanel(new BorderLayout()); buttonContainer.setOpaque(false);
        MyButton createButton = new MyButton(currentTheme,"Modifier",18,currentTheme.blueColor(),currentTheme.blueColor(),"src/main/resources/images/icons/white_add.png",22);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Long dent = Long.parseLong(denteFormGroup.getInputField().getText());
                Double prixPatient = Double.parseDouble(prixDePatientFormGroup.getInputField().getText());
                String noteMedecin = noteMedecinFormGroup.getInputField().getText();
                Acte acte = AppFactory.getActeService().findByLibelle(Objects.requireNonNull(acteFormGroup.getSelectField().getSelectedItem()).toString());
                System.out.println(acte.getPrixDeBase());

                interventionMedecin.setActe(acte);
                interventionMedecin.setNoteMedecin(noteMedecin);
                interventionMedecin.setPrixPatient(prixPatient);
                interventionMedecin.setDent(dent);

                AppFactory.getInterventionController().updateIntervention(interventionMedecin);
            }
        });
        buttonContainer.add(createButton, BorderLayout.WEST);

        bodyPanel.add(formPanel, BorderLayout.CENTER);
        bodyPanel.add(buttonContainer, BorderLayout.SOUTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        return mainPanel;
    }
}

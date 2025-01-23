package ma.CabinetDentaire.presentation.view.dossier_medical;

import ma.CabinetDentaire.App;
import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.entities.enums.TypeConsultation;
import ma.CabinetDentaire.presentation.view.MainView;
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
import java.util.Objects;
import java.util.List;

public class ConsultationPanel extends JPanel {
    private Theme currentTheme;
    private List<Consultation> consultationList;

    private void _init(){
        if(consultationList == null){
            return;
        }

        setLayout(new BorderLayout());
        setOpaque(false);


        updatePanel(consultationListPanel());

    }

    public ConsultationPanel(Theme currentTheme){
        this.currentTheme = currentTheme;
        _init();
    }

    public ConsultationPanel(Theme currentTheme, List<Consultation> consultationList){
        this.currentTheme = currentTheme;
        this.consultationList = consultationList;
        _init();
    }

    public JPanel consultationListPanel(){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        MyLabel title = new MyLabel(currentTheme,"Consultation",22,1);
        add(title, BorderLayout.NORTH);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(new EmptyBorder(15,0,0,0));

        JPanel mainBodyContent = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
        mainBodyContent.setOpaque(false);

        consultationList.forEach(consultation -> {
            JPanel consultationCard = new JPanel(new BorderLayout());

            consultationCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()), // Green bottom border
                    BorderFactory.createEmptyBorder(15, 5, 15, 5) // Padding
            ));

            consultationCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    updatePanel(consultationPanel(consultation));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    consultationCard.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.blueColor()), // Green bottom border
                            BorderFactory.createEmptyBorder(15, 5, 15, 5) // Padding
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    consultationCard.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()), // Green bottom border
                            BorderFactory.createEmptyBorder(15, 5, 15, 5) // Padding
                    ));
                }
            });
            consultationCard.setBackground(currentTheme.fieldsBgColor());
            consultationCard.setPreferredSize(new Dimension(200,200));
            consultationCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JPanel consultationHeader = new JPanel();
            consultationHeader.setOpaque(false);
            consultationHeader.setLayout(new BoxLayout(consultationHeader, BoxLayout.Y_AXIS));
            MyLabel consultationIcon = new MyLabel(currentTheme,"src/main/resources/images/consultation_avatar.png",128);
            consultationIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            consultationHeader.add(consultationIcon);
            consultationCard.add(consultationHeader, BorderLayout.CENTER);

            JPanel consultationFooter = new JPanel();
            consultationFooter.setLayout(new BoxLayout(consultationFooter, BoxLayout.Y_AXIS));
            consultationFooter.setOpaque(false);
            MyLabel consultationName = new MyLabel(currentTheme,"Consultation #" + consultation.getIdConsultation(),20,1);
            MyLabel consultationDate = new MyLabel(currentTheme,consultation.getDateConsultation().toString(),16,0);
            consultationName.setAlignmentX(Component.CENTER_ALIGNMENT);
            consultationDate.setAlignmentX(Component.CENTER_ALIGNMENT);
            consultationFooter.add(consultationName);
            consultationFooter.add(consultationDate);
            consultationCard.add(consultationFooter, BorderLayout.SOUTH);

            mainBodyContent.add(consultationCard);
        });

        bodyPanel.add(mainBodyContent, BorderLayout.CENTER);
        mainPanel.add(bodyPanel,BorderLayout.CENTER);
        return mainPanel;
    }

    public JPanel creerConsultationDeRendezVous(RendezVous rendezVous) {
        JPanel creerConsultationPanel = new JPanel(new BorderLayout());
        creerConsultationPanel.setOpaque(false);
        String title = "Créer un consultation" + (rendezVous != null? " #" + rendezVous.getId() : "");
        MyLabel titleLabel = new MyLabel(currentTheme,title,22,1);
        creerConsultationPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(new EmptyBorder(10,10,10,200));

        JPanel formPanel = new JPanel(new GridLayout(4,1)); formPanel.setOpaque(false);
        SelectFormGroup typeFormGroup = new SelectFormGroup(currentTheme,"Type de consultation","src/main/resources/images/edit_btn.png",new String[]{"CONSULTATION GENERAL","SUIVI","URGENCE"});
        FormGroup dateFormGroup = new FormGroup(currentTheme,"Date de consultation","src/main/resources/images/icons/date.png",rendezVous.getDateRDV().toString());
        formPanel.add(typeFormGroup);
        formPanel.add(dateFormGroup);

        JPanel buttonContainer = new JPanel(new BorderLayout()); buttonContainer.setOpaque(false);
        MyButton createButton = new MyButton(currentTheme,"Créer",18,currentTheme.greenColor(),currentTheme.greenHoverColor(),"src/main/resources/images/icons/white_add.png",22);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    TypeConsultation typeConsultation = (Objects.equals(typeFormGroup.getSelectField().toString(), "CONSULTATION GENERAL") ? TypeConsultation.CONSULTATION_GENERAL : Objects.equals(typeFormGroup.getSelectField().toString(), "SUIVI") ? TypeConsultation.SUIVI : TypeConsultation.URGENCE );
                    LocalDate dateConsultation =LocalDate.parse(dateFormGroup.getInputField().getText());
                    rendezVous.setConsultation(AppFactory.getConsultationController().createConsultation(typeConsultation, dateConsultation,rendezVous.getDossier()));
                    AppFactory.getRendezVousController().updateRendezVous(rendezVous);
                    MainView.updateRightPanel(AppFactory.getDossierMedicalController().showPatientDossierMedicale(rendezVous.getDossier().getId()));
                } catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });
        buttonContainer.add(createButton, BorderLayout.WEST);

        bodyPanel.add(formPanel, BorderLayout.CENTER);
        bodyPanel.add(buttonContainer, BorderLayout.SOUTH);

        creerConsultationPanel.add(bodyPanel, BorderLayout.CENTER);

        return creerConsultationPanel;
    }

    public JPanel consultationPanel(Consultation consultation){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        MyLabel title = new MyLabel(currentTheme,"Consultation #" + consultation.getIdConsultation(),22,1);
        headerPanel.add(title, BorderLayout.WEST);
        MyLabel closeSidePanelBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/back.png",32);
        closeSidePanelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeSidePanelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updatePanel(consultationListPanel());
            }
        });
        headerPanel.add(closeSidePanelBtn, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setOpaque(false);
        bodyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyPanel.add(Box.createVerticalStrut(40));
        MyLabel consultationType = new MyLabel(currentTheme,"Type de consultation : " + consultation.getTypeConsultation(),17,0);
        consultationType.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyPanel.add(consultationType, BorderLayout.NORTH);
        bodyPanel.add(Box.createVerticalStrut(10));
        MyLabel consultationDate = new MyLabel(currentTheme,"Date de consultation : " + consultation.getDateConsultation(),17,0);
        consultationDate.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyPanel.add(consultationDate, BorderLayout.NORTH);
        bodyPanel.add(Box.createVerticalStrut(30));

        if(AppFactory.getOrdonnanceController().findByConsultation(consultation.getIdConsultation()) == null){
            MyButton createOrdonnanceBtn = new MyButton(currentTheme,"Ordonnance",18,currentTheme.greenColor(),currentTheme.greenHoverColor(),"src/main/resources/images/icons/white_add.png",22);
            createOrdonnanceBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            createOrdonnanceBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getOrdonnanceController().createOrdonnancePanel(consultation);
                }
            });
            bodyPanel.add(createOrdonnanceBtn);
        } else {
            MyLabel consultationLabel = new MyLabel(currentTheme,"Afficher l'Ordonnance" ,17,1,"src/main/resources/images/ordonnance.png",32);
            consultationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            consultationLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            consultationLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getOrdonnanceController().displayOrddonnaceById(AppFactory.getOrdonnanceController().findByConsultation(consultation.getIdConsultation()).getIdOrdonnance());
                }
            });
            bodyPanel.add(consultationLabel);
        }

        if(AppFactory.getInterventionController().getAllByConsultation(consultation.getIdConsultation()).size() <= 0){
            bodyPanel.add(Box.createVerticalStrut(10));
            MyButton createInterventionBtn = new MyButton(currentTheme,"Intervention",18,currentTheme.blueColor(),currentTheme.blueHoverColor(),"src/main/resources/images/icons/white_add.png",22);
            createInterventionBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            bodyPanel.add(createInterventionBtn);
            createInterventionBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getInterventionController().createInterventionPanel(consultation);
                }
            });
        } else {
            bodyPanel.add(Box.createVerticalStrut(10));
            MyLabel interventionLabel = new MyLabel(currentTheme,"Afficher les interventions" ,17,1,"src/main/resources/images/header_recette_1.png",32);
            interventionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            interventionLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            interventionLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getInterventionController().findByConsultation(consultation.getIdConsultation());
                }
            });
            bodyPanel.add(interventionLabel);
        }

        if(AppFactory.getFactureService().findByConsultation(consultation.getIdConsultation()).size() <= 0){
            bodyPanel.add(Box.createVerticalStrut(10));
            MyButton createFactureBtn = new MyButton(currentTheme,"Facture",18,currentTheme.blueColor(),currentTheme.blueHoverColor(),"src/main/resources/images/icons/white_add.png",22);
            createFactureBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            createFactureBtn.changeMargin(5,50,5,50);
            createFactureBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        AppFactory.getFactureController().createFacturePanel(consultation);
                    } catch (DaoException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            bodyPanel.add(createFactureBtn);
        } else {
            bodyPanel.add(Box.createVerticalStrut(10));
            MyLabel interventionLabel = new MyLabel(currentTheme,"Afficher les factures" ,17,1,"src/main/resources/images/icons/medicament_prix.png",32);
            interventionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            interventionLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            interventionLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getFactureController().findByConsultation(consultation.getIdConsultation());
                }
            });
            bodyPanel.add(interventionLabel);
        }




        bodyPanel.add(Box.createVerticalStrut(65));

        JPanel actionsButtonContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionsButtonContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        actionsButtonContainer.setOpaque(false);
        MyLabel updateRendezVousBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/green_edit.png",32);
        updateRendezVousBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateRendezVousBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updatePanel(modifierConsultationDeRendezVous(consultation));
            }
        });
        actionsButtonContainer.add(updateRendezVousBtn);
        MyLabel deleteRendezVousBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/red_delete.png",32);
        deleteRendezVousBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteRendezVousBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getConsultationController().deleteConsultationById(consultation.getIdConsultation());
                consultationList = AppFactory.getConsultationController().filterByDossier(consultation.getDossierMedicale().getId());
                updatePanel(consultationListPanel());
            }
        });
        actionsButtonContainer.add(deleteRendezVousBtn);
        bodyPanel.add(actionsButtonContainer);

        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    public JPanel modifierConsultationDeRendezVous(Consultation consultation) {
        JPanel creerConsultationPanel = new JPanel(new BorderLayout());
        creerConsultationPanel.setOpaque(false);
        String title = "Modifier un consultation" + consultation.getIdConsultation();
        MyLabel titleLabel = new MyLabel(currentTheme,title,22,1);
        creerConsultationPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(new EmptyBorder(10,10,10,200));

        JPanel formPanel = new JPanel(new GridLayout(4,1)); formPanel.setOpaque(false);
        SelectFormGroup typeFormGroup = new SelectFormGroup(currentTheme,"Type de consultation","src/main/resources/images/edit_btn.png",new String[]{"CONSULTATION GENERAL","SUIVI","URGENCE"});
        FormGroup dateFormGroup = new FormGroup(currentTheme,"Date de consultation","src/main/resources/images/icons/date.png",consultation.getDateConsultation().toString());
        formPanel.add(typeFormGroup);
        formPanel.add(dateFormGroup);

        JPanel buttonContainer = new JPanel(new BorderLayout()); buttonContainer.setOpaque(false);
        MyButton createButton = new MyButton(currentTheme,"Créer",18,currentTheme.greenColor(),currentTheme.greenHoverColor(),"src/main/resources/images/icons/white_add.png",22);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    TypeConsultation typeConsultation = (Objects.equals(typeFormGroup.getSelectField().toString(), "CONSULTATION GENERAL") ? TypeConsultation.CONSULTATION_GENERAL : Objects.equals(typeFormGroup.getSelectField().toString(), "SUIVI") ? TypeConsultation.SUIVI : TypeConsultation.URGENCE );
                    LocalDate dateConsultation =LocalDate.parse(dateFormGroup.getInputField().getText());
                    AppFactory.getConsultationController().updateConsultation(consultation.getIdConsultation(),typeConsultation, dateConsultation,consultation.getDossierMedicale());
                    new ConsultationPanel(currentTheme,consultationList);
                } catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });
        buttonContainer.add(createButton, BorderLayout.WEST);

        bodyPanel.add(formPanel, BorderLayout.CENTER);
        bodyPanel.add(buttonContainer, BorderLayout.SOUTH);

        creerConsultationPanel.add(bodyPanel, BorderLayout.CENTER);

        return creerConsultationPanel;
    }
    


    public void updatePanel(JPanel panel){
        removeAll();
        revalidate();
        repaint();
        add(panel);
    }

    public List<Consultation> getConsultationList() {
        return consultationList;
    }

    public void setConsultationList(List<Consultation> consultationList) {
        this.consultationList = consultationList;
    }


}



//    private JPanel consultationPanel(){
//        JPanel mainPanel = new JPanel(new BorderLayout());
//        mainPanel.setOpaque(false);
//        MyLabel title = new MyLabel(currentTheme,"Consultation",22,1);
//        mainPanel.add(title, BorderLayout.NORTH);
//
//        JPanel bodyPanel = new JPanel(new BorderLayout());
//        bodyPanel.setOpaque(false);
//        JPanel mainBodyContent = new JPanel(new BorderLayout());
//        mainBodyContent.setBackground(Color.blue);
//
//
//
//        bodyPanel.add(mainBodyContent, BorderLayout.CENTER);
//        JPanel sideBodyContent = new JPanel(new BorderLayout());
//        sideBodyContent.setBackground(Color.yellow);
//        sideBodyContent.setPreferredSize(new Dimension(400, 100));
//        bodyPanel.add(sideBodyContent, BorderLayout.EAST);
//
//        mainPanel.add(bodyPanel, BorderLayout.CENTER);
//
//
//        return mainPanel;
//    }
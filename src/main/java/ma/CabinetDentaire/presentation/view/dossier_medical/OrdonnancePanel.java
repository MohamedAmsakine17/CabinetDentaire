package ma.CabinetDentaire.presentation.view.dossier_medical;

import ma.CabinetDentaire.App;
import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.Medicament;
import ma.CabinetDentaire.entities.Ordonnance;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.form.FormGroup;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

public class OrdonnancePanel extends JPanel {
    Theme currentTheme;

    public OrdonnancePanel(Theme currentTheme) {
        this.currentTheme = currentTheme;
    }

    public JPanel ordonnanceAllPanels(List<Ordonnance> ordonnances) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Les Ordonnances ",22,1);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel ordonnacesListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
        ordonnacesListPanel.setOpaque(false);

        for(Ordonnance ordonnance : ordonnances) {
            JPanel ordonnanceCard = new JPanel(new BorderLayout());
            ordonnanceCard.setBackground(currentTheme.fieldsBgColor());
            ordonnanceCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            ordonnanceCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()),
                    BorderFactory.createEmptyBorder(20, 10, 20, 10)
            ));
            MyLabel ordonnanceTitle = new MyLabel(currentTheme,"Ordonnance " + ordonnance.getIdOrdonnance(),18,1);
            ordonnanceCard.add(ordonnanceTitle, BorderLayout.NORTH);
            MyLabel ordonnanceDate = new MyLabel(currentTheme,ordonnance.getDate().toString(),14,0);
            ordonnanceCard.add(ordonnanceDate, BorderLayout.CENTER);

            ordonnanceCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getOrdonnanceController().displayOrddonnaceById(ordonnance.getIdOrdonnance());
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    ordonnanceCard.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.blueColor()),
                            BorderFactory.createEmptyBorder(20, 10, 20, 10)
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    ordonnanceCard.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()),
                            BorderFactory.createEmptyBorder(20, 10, 20, 10)
                    ));
                }
            });

            ordonnacesListPanel.add(ordonnanceCard);
        }
        mainPanel.add(ordonnacesListPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    public JPanel ordonnacePanel(Ordonnance ordonnance) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        MyLabel title = new MyLabel(currentTheme,"Ordonnance #" + ordonnance.getIdOrdonnance(),22,1);
        headerPanel.add(title, BorderLayout.WEST);
        MyLabel backBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/back.png",32);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getOrdonnanceController().findAll();
            }
        });
        headerPanel.add(backBtn, BorderLayout.EAST);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        MyLabel date=  new MyLabel(currentTheme,ordonnance.getDate().toString(),15,1,"src/main/resources/images/creation_date.png",20);
        date.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, currentTheme.greenColor()),
                BorderFactory.createEmptyBorder(0, 0, 10, 0)
        ));
        bodyPanel.add(date, BorderLayout.NORTH);

        JPanel prescriptionPanel = new JPanel(new BorderLayout());
        prescriptionPanel.setOpaque(false);
        prescriptionPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JPanel prescriptionHeaderPanel = new JPanel(new BorderLayout());
        prescriptionHeaderPanel.setOpaque(false);
        MyLabel prescriptionTitle=  new MyLabel(currentTheme,"Prescription de medicament",18,0);
        prescriptionHeaderPanel.add(prescriptionTitle, BorderLayout.WEST);
        MyLabel prescriptionAddBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/green_add.png",28);
        prescriptionAddBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        prescriptionAddBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getPrescriptionController().createPrescription(ordonnance);
            }
        });
        prescriptionHeaderPanel.add(prescriptionAddBtn, BorderLayout.EAST);

        prescriptionPanel.add(prescriptionHeaderPanel, BorderLayout.NORTH);

        JPanel prescriptionListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
        prescriptionListPanel.setOpaque(false);

        AppFactory.getPrescriptionController().findByOrdonnance(ordonnance.getIdOrdonnance()).forEach(prescription -> {
            JPanel prescriptionCard = new JPanel(new BorderLayout());
            prescriptionCard.setBackground(currentTheme.fieldsBgColor());
            prescriptionCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            prescriptionCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()),
                    BorderFactory.createEmptyBorder(30, 10, 30, 10)
            ));
            MyLabel prescriptionCardTitle = new MyLabel(currentTheme,"Prescription #" + prescription.getIdPrescription(),18,1);
            prescriptionCard.add(prescriptionCardTitle, BorderLayout.CENTER);

            prescriptionCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getPrescriptionController().displayPrescriptionDeMedicament(prescription);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    prescriptionCard.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.blueColor()),
                            BorderFactory.createEmptyBorder(30, 10, 30, 10)
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    prescriptionCard.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()),
                            BorderFactory.createEmptyBorder(30, 10, 30, 10)
                    ));
                }
            });

            prescriptionListPanel.add(prescriptionCard);
        });

        prescriptionPanel.add(prescriptionListPanel, BorderLayout.CENTER);

        bodyPanel.add(prescriptionPanel, BorderLayout.CENTER);

        JPanel actionsButtonContainer = new JPanel(new BorderLayout());
        actionsButtonContainer.setOpaque(false);
        MyLabel updateButton = new MyLabel(currentTheme,"src/main/resources/images/icons/green_edit.png",32);
        updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getOrdonnanceController().modifierOrdonnancePanel(ordonnance);
            }
        });
        actionsButtonContainer.add(updateButton,BorderLayout.WEST);
        MyLabel deleteButton = new MyLabel(currentTheme,"src/main/resources/images/icons/red_delete.png",32);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getOrdonnanceController().deleteOrdonnanceById(ordonnance.getIdOrdonnance(),ordonnance.getConsultationConcernee().getDossierMedicale().getId());
            }
        });
        actionsButtonContainer.add(deleteButton,BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        mainPanel.add(actionsButtonContainer,BorderLayout.SOUTH);
        return mainPanel;
    }

    public JPanel createOrdonnance(Consultation consultation) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Creation de ordonnance",22,1);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(new EmptyBorder(10,10,10,200));

        JPanel formPanel = new JPanel(new GridLayout(4,1)); formPanel.setOpaque(false);
        FormGroup dateFormGroup = new FormGroup(currentTheme,"Date d'ordonnance","src/main/resources/images/icons/date.png", consultation.getDateConsultation().toString());
        dateFormGroup.setBorder(BorderFactory.createEmptyBorder(0,0,0,400));
        formPanel.add(dateFormGroup);

        JPanel buttonContainer = new JPanel(new BorderLayout()); buttonContainer.setOpaque(false);
        MyButton createButton = new MyButton(currentTheme,"Créer",18,currentTheme.greenColor(),currentTheme.greenHoverColor(),"src/main/resources/images/icons/white_add.png",22);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getOrdonnanceController().createOrdonnance(LocalDate.parse(dateFormGroup.getInputField().getText()),consultation);
            }
        });
        buttonContainer.add(createButton, BorderLayout.WEST);

        bodyPanel.add(formPanel, BorderLayout.CENTER);
        bodyPanel.add(buttonContainer, BorderLayout.SOUTH);

        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        return mainPanel;
    }



    public JPanel modifierOrdonnance(Ordonnance ordonnance) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Creation de ordonnance",22,1);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(new EmptyBorder(10,10,10,200));

        JPanel formPanel = new JPanel(new GridLayout(4,1)); formPanel.setOpaque(false);
        FormGroup dateFormGroup = new FormGroup(currentTheme,"Date d'ordonnance","src/main/resources/images/icons/date.png", ordonnance.getDate().toString());
        dateFormGroup.setBorder(BorderFactory.createEmptyBorder(0,0,0,400));
        formPanel.add(dateFormGroup);

        JPanel buttonContainer = new JPanel(new BorderLayout()); buttonContainer.setOpaque(false);
        MyButton createButton = new MyButton(currentTheme,"Modifier",18,currentTheme.blueColor(),currentTheme.blueColor(),"src/main/resources/images/icons/white_add.png",22);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ordonnance.setDate(LocalDate.parse(dateFormGroup.getInputField().getText()));
                AppFactory.getOrdonnanceController().updateOrdonnance(ordonnance);
            }
        });
        buttonContainer.add(createButton, BorderLayout.WEST);

        bodyPanel.add(formPanel, BorderLayout.CENTER);
        bodyPanel.add(buttonContainer, BorderLayout.SOUTH);

        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        return mainPanel;
    }
}
